package com.example.toyproject.modules.academy.controller;

import com.example.toyproject.modules.academy.AcademyModelAssembler;
import com.example.toyproject.modules.academy.dto.AcademyModel;
import com.example.toyproject.modules.academy.entity.Academy;
import com.example.toyproject.modules.academy.enums.AcademyConst;
import com.example.toyproject.modules.academy.request.AcademySaveRequest;
import com.example.toyproject.modules.academy.service.AcademyService;
import com.example.toyproject.modules.common.exception.Exception400;
import com.example.toyproject.modules.user.entity.User;
import com.example.toyproject.modules.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/academies")
public class AcademyController {

    private final AcademyService academyService;

    private final UserService userService;

    public AcademyController(AcademyService academyService, UserService userService) {
        this.academyService = academyService;
        this.userService = userService;
    }

    @PostMapping("/join")
    public ResponseEntity<AcademyModel> saveAcademy(
            @Valid @RequestBody AcademySaveRequest request, Errors errors
    ) {
        if (errors.hasErrors()) {
            throw new Exception400(errors.getAllErrors().get(0).getDefaultMessage());
        }

        User saveUser = userService.save(request.request());

        Academy saveAcademy = academyService.save(request, saveUser);

        return ResponseEntity.ok(
                new AcademyModelAssembler().toModel(saveAcademy)
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
