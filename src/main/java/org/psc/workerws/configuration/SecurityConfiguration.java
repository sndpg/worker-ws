package org.psc.workerws.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.UserDetailsServiceLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder builder) {
        return builder.getOrBuild();
    }

//    @Bean
//    public DigestAuthenticationEntryPoint digestAuthenticationEntryPoint() {
//        var digestAuthenticationEntryPoint = new DigestAuthenticationEntryPoint();
//        digestAuthenticationEntryPoint.setKey("digestKey");
//        digestAuthenticationEntryPoint.setRealmName("worker-ws@example.org");
//        return digestAuthenticationEntryPoint;
//    }
//
//    @Bean
//    public DigestAuthenticationFilter digestAuthenticationFilter(
//            DigestAuthenticationEntryPoint digestAuthenticationEntryPoint,
//            AuthenticationManagerBuilder authenticationManagerBuilder) {
//        var digestAuthenticationFilter = new DigestAuthenticationFilter();
//        digestAuthenticationFilter.setAuthenticationEntryPoint(digestAuthenticationEntryPoint);
//        digestAuthenticationFilter.setUserDetailsService(userDetailsService());
//        return digestAuthenticationFilter;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/token**")
                .authenticated()
                .antMatchers("/randomNumber**")
                .authenticated()
                .antMatchers("/**")
                .permitAll()
                .and()
                .httpBasic()
                .and()
                // registers itself automatically on POST /login (since it's a UsernamePasswordAuthenticationFilter
                //.addFilter(new LdapAuthenticationToJwtTokenFilter(new AntPathRequestMatcher("/auth", "GET")))
                //.addFilterBefore(digestAuthenticationFilter(null, null), BasicAuthenticationToJwtTokenFilter.class)
                .addFilter(new BasicAuthenticationToJwtTokenFilter(authenticationManager(null)))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(null)))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Profile("ldap")
    @Configuration
    class LdapAuthenticationConfiguration {

        @Autowired
        public void configureLdapAuthenticationManager(AuthenticationManagerBuilder auth) throws Exception {
            auth.ldapAuthentication()
                    .userDnPatterns("uid={0},ou=people")
                    .groupSearchBase("ou=groups")
                    .contextSource()
                    //.url("ldap://localhost:8389/dc=springframework,dc=org")
                    .ldif("classpath:ldap-server.ldif")
                    .and()
                    .passwordCompare()
                    .passwordEncoder(new LdapShaPasswordEncoder())
                    .passwordAttribute("userPassword")
//                    .and()
//                    .ldapAuthoritiesPopulator(new UserDetailsServiceLdapAuthoritiesPopulator(userDetailsService()))
            ;
        }

    }

    @Profile("!ldap")
    @Configuration
    static class InMemoryAuthenticationConfiguration {

        @Autowired
        public void configureAuthenticationManager(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                    .withUser("sykq")
                    .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("password"))
                    .authorities("ROLE_USER");
        }

    }


    @RequiredArgsConstructor
    private static class LdapUserDetailsServiceAdapter implements UserDetailsService {

        private final LdapUserDetailsMapper ldapUserDetailsMapper;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return null;
        }
    }

}
