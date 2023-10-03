package com.example.toyproject.modules.parents.service;

import com.example.toyproject.modules.parents.entity.Parents;
import com.example.toyproject.modules.parents.repository.ParentsRepository;
import com.example.toyproject.modules.parents.request.ParentsSaveRequest;
import com.example.toyproject.modules.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParentsService {

    private final ParentsRepository parentsRepository;

    public ParentsService(ParentsRepository parentsRepository) {
        this.parentsRepository = parentsRepository;
    }

    public Parents save(ParentsSaveRequest request, User user) {
        return parentsRepository.save(request.toEntity(user));
    }

    public Optional<Parents> getParents(Long id) {
        return parentsRepository.findById(id);
    }
}
