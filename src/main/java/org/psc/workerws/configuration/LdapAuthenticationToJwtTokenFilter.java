package org.psc.workerws.configuration;

import com.auth0.jwt.JWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class LdapAuthenticationToJwtTokenFilter extends AbstractAuthenticationProcessingFilter {

    private static final String SECRET = "SecretKeyToGenJWTs";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_STRING = "Authorization";

    private final UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;

    public LdapAuthenticationToJwtTokenFilter(RequestMatcher requestMatcher){
        super(requestMatcher);
        usernamePasswordAuthenticationFilter = new UsernamePasswordAuthenticationFilter();
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        return usernamePasswordAuthenticationFilter.attemptAuthentication(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) {
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(Date.from(LocalDateTime.now()
                        .plus(Duration.of(15, ChronoUnit.MINUTES))
                        .atZone(ZoneId.systemDefault())
                        .toInstant()))
                .sign(HMAC512(SECRET.getBytes()));
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }

}

