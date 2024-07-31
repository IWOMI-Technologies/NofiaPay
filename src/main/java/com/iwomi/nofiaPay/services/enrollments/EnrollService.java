package com.iwomi.nofiaPay.services.enrollments;


import com.iwomi.nofiaPay.core.mappers.IEnrollMapper;
import com.iwomi.nofiaPay.dtos.EnrollDto;
import com.iwomi.nofiaPay.dtos.response.Enroll;
import com.iwomi.nofiaPay.frameworks.data.repositories.enrollments.EnrollRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnrollService  implements IEnrollService {

    private  final EnrollRepository enrollRepository;

    private  final IEnrollMapper mapper;


    @Override
    public List<Enroll> viewAllUsers() {
        return enrollRepository.getAllUsers()
                .stream()
                .map(mapper::mapToModel)
                .toList();
    }

    @Override
    public Enroll saveUser(EnrollDto dto) {
        return mapper.mapToModel(enrollRepository.createUser(dto));
    }

    @Override
    public Enroll viewOne(UUID uuid) {
        return mapper.mapToModel(enrollRepository.getOne(uuid));
    }

    @Override
    public Enroll update(UUID uuid, EnrollDto dto) {
        return mapper.mapToModel(enrollRepository.updateUser(uuid , dto));
    }

    @Override
    public void deleteOne(UUID uuid) {
           enrollRepository.deleteUser(uuid);
    }

    @Override
    public Enroll viewByPhoneNumber(String phoneNumber) {
        return mapper.mapToModel(enrollRepository.getByPhone(phoneNumber));
    }
}
