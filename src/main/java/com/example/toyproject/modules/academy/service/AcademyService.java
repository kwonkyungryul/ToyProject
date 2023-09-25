package com.example.toyproject.modules.academy.service;

import com.example.toyproject.modules.academy.entity.Academy;
import com.example.toyproject.modules.academy.repository.AcademyRepository;
import com.example.toyproject.modules.academy.request.AcademySaveRequest;
import com.example.toyproject.modules.common.request.LoginRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AcademyService {

    private final AcademyRepository academyRepository;

    public AcademyService(AcademyRepository academyRepository) {
        this.academyRepository = academyRepository;
    }

    public Optional<Academy> getAcademy(Long id) {
        return academyRepository.findById(id);
    }

    public Academy save(AcademySaveRequest request) {
        return academyRepository.save(request.toEntity());
    }
}
