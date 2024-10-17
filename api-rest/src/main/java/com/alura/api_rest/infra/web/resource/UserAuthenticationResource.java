package com.alura.api_rest.infra.web.resource;

import com.alura.api_rest.domain.model.token.JwtDataTokenDTO;
import com.alura.api_rest.domain.model.user.DataAuthLogin;
import com.alura.api_rest.domain.model.user.UserApp;
import com.alura.api_rest.infra.config.security.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/login")
public class UserAuthenticationResource {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<JwtDataTokenDTO> doLogin(@RequestBody DataAuthLogin dataAuthLogin) {
        log.info("Starting Login...");
        var authenticationToken = new UsernamePasswordAuthenticationToken(dataAuthLogin.getUsername(), dataAuthLogin.getPassword());
        var authentication = authenticationManager.authenticate(authenticationToken);
        log.info("Generating JWT");
        var tokenJWT = tokenService.generateToken((UserApp) authentication.getPrincipal());
        log.debug("Generated JWT: {}", tokenJWT);
        return ResponseEntity.ok(new JwtDataTokenDTO(tokenJWT));
    }

}
