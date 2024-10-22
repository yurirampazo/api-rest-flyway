package com.alura.api_rest.domain.constants;

public final class SecurityFilterConstants {
    private SecurityFilterConstants() {
    }

    public static final String[] SKIP_FILTER_PATHS = {
            "/login",
            "/sign-up",
            "/v3/api-docs/**",
            "/swagger-ui/**"
   };

}
