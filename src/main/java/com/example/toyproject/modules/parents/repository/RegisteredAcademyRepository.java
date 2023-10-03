package com.example.toyproject.modules.parents.repository;

import com.example.toyproject.modules.parents.entity.RegisteredAcademy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisteredAcademyRepository extends JpaRepository<RegisteredAcademy, Long> {
}
