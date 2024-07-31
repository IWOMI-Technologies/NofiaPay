package com.iwomi.nofiaPay.frameworks.data.repositories.enrollments;

import com.iwomi.nofiaPay.core.errors.exceptions.GeneralException;
import com.iwomi.nofiaPay.core.mappers.IEnrollMapper;
import com.iwomi.nofiaPay.dtos.EnrollDto;
import com.iwomi.nofiaPay.frameworks.data.entities.EnrollEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EnrollRepository {

    private  final IEnrollRepository repository;

    private  final IEnrollMapper mapper;


  public List<EnrollEntity> getAllUsers() {
        return repository.findAll();
    }
    public EnrollEntity createUser( EnrollDto dto){
        EnrollEntity enroll = mapper.mapToEntity(dto);
        return repository.save(enroll);
    }

    public EnrollEntity getOne(UUID uuid) {
        return repository.findById(uuid)
                .orElseThrow(() -> new GeneralException("Enrolled not found."));
    }

    public EnrollEntity updateUser(UUID uuid, EnrollDto dto) {
        EnrollEntity entity = getOne(uuid);
        mapper.updateUserFromDto(dto, entity);
        return repository.save(entity);
    }

    public  void  deleteUser(UUID uuid) {
        repository.deleteById(uuid);
    }

    public EnrollEntity getByPhone(String phoneNumber) {
        return repository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new GeneralException("Enrolled not found."));
    }
}
