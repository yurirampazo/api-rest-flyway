package com.alura.api_rest.infra.web.resource;

import com.alura.api_rest.domain.model.token.JwtDataTokenDTO;
import com.alura.api_rest.domain.model.user.DataAuthLogin;
import com.alura.api_rest.domain.model.user.DataRegisteredDTO;
import com.alura.api_rest.domain.model.user.UserApp;
import com.alura.api_rest.infra.config.security.TokenService;
import com.alura.api_rest.infra.config.security.UserAuthService;
import com.alura.api_rest.infra.persist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class UserAuthenticationResource {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserAuthService userAuthService;

    @PostMapping(value = "/login")
    public ResponseEntity<JwtDataTokenDTO> doLogin(@RequestBody DataAuthLogin dataAuthLogin) {
        log.info("Starting Login...");
        var authenticationToken = new UsernamePasswordAuthenticationToken(dataAuthLogin.getUsername(), dataAuthLogin.getPassword());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var tokenJWT = tokenService.generateToken((UserApp) authentication.getPrincipal());
        log.debug("Generated JWT: {}", tokenJWT);
        return ResponseEntity.ok(new JwtDataTokenDTO(tokenJWT));
    }

    @PostMapping(value = "/sign-up")
    public ResponseEntity<DataRegisteredDTO> register(@RequestBody DataAuthLogin dataAuthLogin,
                                                      UriComponentsBuilder uriComponentsBuilder) {
        log.info("Starting Register...");
        var user = userAuthService.saveUser(dataAuthLogin);
        log.info("Saved user: {}", user.getUsername());
        var uri = uriComponentsBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }



}
