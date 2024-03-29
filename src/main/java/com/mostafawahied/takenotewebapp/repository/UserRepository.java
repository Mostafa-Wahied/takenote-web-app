package com.mostafawahied.takenotewebapp.repository;

import com.mostafawahied.takenotewebapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findUserByEmail(String email);

    User findUserByResetPasswordToken(String token);
}
