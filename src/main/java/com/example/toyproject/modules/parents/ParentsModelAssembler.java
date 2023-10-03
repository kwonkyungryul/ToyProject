package com.example.toyproject.modules.parents;

import com.example.toyproject.modules.parents.controller.ParentsController;
import com.example.toyproject.modules.parents.dto.ParentsModel;
import com.example.toyproject.modules.parents.entity.Parents;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ParentsModelAssembler extends RepresentationModelAssemblerSupport<Parents, ParentsModel> {
    public ParentsModelAssembler() {
        super(ParentsController.class, ParentsModel.class);
    }

    @Override
    public ParentsModel toModel(Parents parents) {
        ParentsModel resource = new ParentsModel(parents);
        resource.add(linkTo(methodOn(ParentsController.class).getParents(parents.getId())).withSelfRel());
        return resource;
    }

    @Override
    public CollectionModel<ParentsModel> toCollectionModel(Iterable<? extends Parents> entities) {
        return super.toCollectionModel(entities);
    }
}
