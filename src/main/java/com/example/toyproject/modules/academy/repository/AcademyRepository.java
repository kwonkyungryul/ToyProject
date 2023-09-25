package com.example.toyproject.modules.academy.repository;

import com.example.toyproject.modules.academy.entity.Academy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademyRepository extends JpaRepository<Academy, Long> {
}
