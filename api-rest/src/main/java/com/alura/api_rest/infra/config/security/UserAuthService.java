package com.alura.api_rest.infra.config.security;

import com.alura.api_rest.app.utils.AppUtils;
import com.alura.api_rest.domain.model.user.DataAuthLogin;
import com.alura.api_rest.domain.model.user.DataRegisteredDTO;
import com.alura.api_rest.domain.model.user.UserApp;
import com.alura.api_rest.infra.persist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public DataRegisteredDTO saveUser(DataAuthLogin request) {
        var userApp = UserApp.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        var save = userRepository.save(userApp);
        return DataRegisteredDTO.builder()
                .id(save.getId())
                .username(AppUtils.maskEmail(userApp.getUsername()))
                .password("************")
                .build();
    }


}
