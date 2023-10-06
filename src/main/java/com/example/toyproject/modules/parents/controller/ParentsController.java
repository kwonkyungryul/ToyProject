package com.example.toyproject.modules.parents.controller;

import com.example.toyproject.modules.common.exception.Exception400;
import com.example.toyproject.modules.parents.ParentsModelAssembler;
import com.example.toyproject.modules.parents.dto.ParentsModel;
import com.example.toyproject.modules.parents.entity.Parents;
import com.example.toyproject.modules.parents.enums.ParentsConst;
import com.example.toyproject.modules.parents.request.ParentsSaveRequest;
import com.example.toyproject.modules.parents.service.ParentsService;
import com.example.toyproject.modules.parents.service.RegisteredAcademyService;
import com.example.toyproject.modules.user.entity.User;
import com.example.toyproject.modules.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/parents")
public class ParentsController {

    private final ParentsService parentsService;

    private final RegisteredAcademyService registeredAcademyService;

    private final UserService userService;

    public ParentsController(ParentsService parentsService, RegisteredAcademyService registeredAcademyService, UserService userService) {
        this.parentsService = parentsService;
        this.registeredAcademyService = registeredAcademyService;
        this.userService = userService;
    }

    @PostMapping("/join")
    public ResponseEntity<ParentsModel> saveParents(
            @Valid @RequestBody ParentsSaveRequest request, Errors errors
    ) {
        if (errors.hasErrors()) {
            throw new Exception400(errors.getAllErrors().get(0).getDefaultMessage());
        }

        User saveUser = userService.save(request.request());

        Parents saveParents = parentsService.save(request, saveUser);

        return ResponseEntity.ok(
                new ParentsModelAssembler().toModel(saveParents)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParentsModel> getParents(
            @PathVariable Long id
    ) {
        Optional<Parents> optionalParents = parentsService.getParents(id);

        if (optionalParents.isEmpty()) {
            throw new Exception400(ParentsConst.notFound);
        }

        return ResponseEntity.ok(
                new ParentsModelAssembler().toModel(optionalParents.get())
        );
    }
}
