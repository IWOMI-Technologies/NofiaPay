package com.iwomi.authms.frameworks.data.repositories;


import com.iwomi.authms.frameworks.data.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByPhoneNumber(String phone);
    Boolean existsUsersEntityByPhoneNumber(String phone);
}
