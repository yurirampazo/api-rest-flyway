package com.alura.api_rest.infra.config.security;

import com.alura.api_rest.infra.persist.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    /**
     * Gets a subject from token, subject here is the same as a username
     * @param request http request
     * @param response httpresponse
     * @param filterChain chain of filters
     * @throws ServletException might be trown
     * @throws IOException might be thrown
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final var LOGIN = "/crm-api/login";
        log.info("Starting internal filter...");
        var requestPath = request.getRequestURI();
        if (LOGIN.equals(requestPath)) {
            log.debug("Skipping token validation for login request.");
            filterChain.doFilter(request, response);
            return;
        }
        var jwtToken = getJwtToken(request);
        log.debug("Request JWT Token: {}", jwtToken);
        var subjectFromToken = tokenService.getSubjectFromToken(jwtToken);
        var user = userRepository.findByUsername(subjectFromToken);
        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private String getJwtToken(HttpServletRequest request) {
      var authorizationHeader = request.getHeader("Authorization");
      if(StringUtils.isBlank(authorizationHeader)) {
          throw new IllegalArgumentException("JWT missing on request headers");
      }
      return authorizationHeader;
    }
}
