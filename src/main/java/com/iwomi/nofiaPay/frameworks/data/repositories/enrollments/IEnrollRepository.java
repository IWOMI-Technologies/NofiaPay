package com.iwomi.nofiaPay.frameworks.data.repositories.enrollments;


import com.iwomi.nofiaPay.frameworks.data.entities.EnrollEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IEnrollRepository extends JpaRepository<EnrollEntity, UUID> {
    Optional<EnrollEntity> findByPhoneNumber(String phoneNumber);
}
