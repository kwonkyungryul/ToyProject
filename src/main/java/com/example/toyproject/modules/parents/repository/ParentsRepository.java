package com.example.toyproject.modules.parents.repository;

import com.example.toyproject.modules.parents.entity.Parents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentsRepository extends JpaRepository<Parents, Long> {
}
