package com.iwomi.nofiaPay.services.enrollments;

import com.iwomi.nofiaPay.dtos.EnrollDto;
import com.iwomi.nofiaPay.dtos.responses.Enroll;

import java.util.List;
import java.util.UUID;

public interface IEnrollService {

    List<Enroll> viewAllUsers();
    Enroll saveUser(EnrollDto dto);
    Enroll viewOne(UUID uuid);
    Enroll update(UUID uuid, EnrollDto dto);
    void deleteOne(UUID uuid);
    Enroll viewByPhoneNumber(String phoneNumber);
}
