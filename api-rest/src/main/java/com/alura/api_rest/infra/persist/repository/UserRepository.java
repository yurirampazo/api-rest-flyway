package com.alura.api_rest.infra.persist.repository;

import com.alura.api_rest.domain.model.user.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<UserApp, Long> {
    UserDetails findByUsername(String username);


}
