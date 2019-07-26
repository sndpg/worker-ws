package org.psc.workerws.configuration;

import com.auth0.jwt.JWT;
import io.vavr.control.Try;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class BasicAuthenticationToJwtTokenFilter extends BasicAuthenticationFilter {

    private static final String SECRET = "SecretKeyToGenJWTs";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_STRING = "Authorization";

    public BasicAuthenticationToJwtTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public BasicAuthenticationToJwtTokenFilter(AuthenticationManager authenticationManager,
                                               AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        super.doFilterInternal(request, response, chain);
    }

    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              Authentication authResult) {
        Authentication authentication =
                Try.of(() -> SecurityContextHolder.getContext().getAuthentication()).getOrNull();

        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            addTokenToHeader(request, response, authentication);
        }
    }

    private void addTokenToHeader(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
        String token = JWT.create()
                .withSubject(((LdapUserDetails) auth.getPrincipal()).getUsername())
                .withExpiresAt(Date.from(LocalDateTime.now()
                        .plus(Duration.of(15, ChronoUnit.MINUTES))
                        .atZone(ZoneId.systemDefault())
                        .toInstant()))
                .sign(HMAC512(SECRET.getBytes()));
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }

}

