package com.example.toyproject.modules.parents.service;

import com.example.toyproject.modules.parents.repository.RegisteredAcademyRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisteredAcademyService {

    private final RegisteredAcademyRepository registeredAcademyRepository;

    public RegisteredAcademyService(RegisteredAcademyRepository registeredAcademyRepository) {
        this.registeredAcademyRepository = registeredAcademyRepository;
    }


}
