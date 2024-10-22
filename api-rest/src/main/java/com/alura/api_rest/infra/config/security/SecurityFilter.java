package com.alura.api_rest.infra.config.security;

import com.alura.api_rest.infra.persist.repository.UserRepository;
import com.google.gson.Gson;
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
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.alura.api_rest.domain.constants.SecurityFilterConstants.SKIP_FILTER_PATHS;

@Log4j2
@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();


    /**
     * Gets a subject from token, subject here is the same as a username
     *
     * @param request     http request
     * @param response    httpresponse
     * @param filterChain chain of filters
     * @throws ServletException might be trown
     * @throws IOException      might be thrown
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            log.info("Starting internal filter...");
            var requestPath = request.getRequestURI();
            log.info("Attempting to access: {}", requestPath);
            for (String skipPath : SKIP_FILTER_PATHS) {
                if (antPathMatcher.match(skipPath, requestPath)) {
                    log.debug("Skipping token validation for: {}", requestPath);
                    filterChain.doFilter(request, response);
                    return;
                }
            }
            var jwtToken = getJwtToken(request);
            if (jwtToken == null) {
                log.warn("JWT Token is missing. Skipping authentication.");
                filterChain.doFilter(request, response);
                return;
            }

            log.info("Request JWT Token: {}", jwtToken);
            var subjectFromToken = tokenService.getSubjectFromToken(jwtToken);
            var user = userRepository.findByUsername(subjectFromToken);
            log.info("User found: {}", new Gson().toJson(user));

            if (ObjectUtils.isEmpty(user)) {
                log.info("User not found: {}", subjectFromToken);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
                return;
            }

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Authentication set for user: {}", user.getUsername());
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Error processing filter", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private String getJwtToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (StringUtils.isBlank(authorizationHeader)) {
           return null;
        }
        return authorizationHeader;
    }
}
