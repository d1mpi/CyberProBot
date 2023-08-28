package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.UserEntity;
import org.example.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void createUser(UserEntity userEntity) {
        try {
            userRepo.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            log.warn(e.getMessage());
        }
    }

    public UserEntity getUserByTelegramId(Long id) {
        UserEntity user = userRepo.getUserByTelegramId(id);
        if (user == null) {
            throw new EntityNotFoundException("User with this ID not found");
        }
        return userRepo.getUserByTelegramId(id);
    }
}
