package com.example.toyproject.modules.academy;

import com.example.toyproject.modules.academy.controller.AcademyController;
import com.example.toyproject.modules.academy.dto.AcademyModel;
import com.example.toyproject.modules.academy.entity.Academy;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class AcademyModelAssembler extends RepresentationModelAssemblerSupport<Academy, AcademyModel> {
    public AcademyModelAssembler() {
        super(AcademyController.class, AcademyModel.class);
    }

    @Override
    public AcademyModel toModel(Academy academy) {
        AcademyModel resource = new AcademyModel(academy);
        resource.add(linkTo(methodOn(AcademyController.class).getAcademy(academy.getId())).withSelfRel());
        return resource;
    }

    @Override
    public CollectionModel<AcademyModel> toCollectionModel(Iterable<? extends Academy> entities) {
        return super.toCollectionModel(entities);
    }
}
