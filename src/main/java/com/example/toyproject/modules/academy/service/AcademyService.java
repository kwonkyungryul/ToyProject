package com.example.toyproject.modules.academy.service;

import com.example.toyproject.modules.academy.entity.Academy;
import com.example.toyproject.modules.academy.repository.AcademyRepository;
import com.example.toyproject.modules.academy.request.AcademySaveRequest;
import com.example.toyproject.modules.common.request.LoginRequest;
import com.example.toyproject.modules.user.entity.User;
import com.example.toyproject.util.SerialGenerator;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public Academy save(AcademySaveRequest request, User user) {
        Academy academy = request.toEntity(user);
        academy.setCode(makeToken(request.academyName()));
        return academyRepository.save(academy);
    }

    public String makeToken(String academyName) {
        return SerialGenerator.generateSerial(academyName);
    }
}
