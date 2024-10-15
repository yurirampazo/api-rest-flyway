package com.alura.api_rest.infra.web.resource;

import com.alura.api_rest.domain.model.user.DataAuthLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login")
public class UserAuthenticationResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<Void> doLogin(@RequestBody DataAuthLogin dataAuthLogin) {
        var token = new UsernamePasswordAuthenticationToken(dataAuthLogin.getUsername(), dataAuthLogin.getPassword());
        var authenticate = authenticationManager.authenticate(token);

        return ResponseEntity.ok().build();
    }

}
