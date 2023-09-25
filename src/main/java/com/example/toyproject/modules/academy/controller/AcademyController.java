package com.example.toyproject.modules.academy.controller;

import com.example.toyproject.modules.academy.AcademyModelAssembler;
import com.example.toyproject.modules.academy.dto.AcademyModel;
import com.example.toyproject.modules.academy.entity.Academy;
import com.example.toyproject.modules.academy.enums.AcademyConst;
import com.example.toyproject.modules.academy.request.AcademySaveRequest;
import com.example.toyproject.modules.academy.service.AcademyService;
import com.example.toyproject.modules.common.exception.Exception400;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/academies")
public class AcademyController {

    private final AcademyService academyService;

    public AcademyController(AcademyService academyService) {
        this.academyService = academyService;
    }

    @PostMapping("/join")
    public ResponseEntity<AcademyModel> saveAcademy(
            @Valid @RequestBody AcademySaveRequest request, Errors errors
    ) {
        if (errors.hasErrors()) {
            throw new Exception400(errors.getAllErrors().get(0).getDefaultMessage());
        }

        Academy academy = academyService.save(request);

        return ResponseEntity.ok(
                new AcademyModelAssembler().toModel(academy)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcademyModel> getAcademy(@PathVariable Long id) {

        Optional<Academy> optionalAcademy = academyService.getAcademy(id);

        if (optionalAcademy.isEmpty()) {
            throw new Exception400(AcademyConst.notFound);
        }

        return ResponseEntity.ok(
                new AcademyModelAssembler().toModel(optionalAcademy.get())
        );
    }
}
