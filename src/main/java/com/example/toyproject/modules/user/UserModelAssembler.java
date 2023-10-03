package com.example.toyproject.modules.user;

import com.example.toyproject.config.security.CustomUserDetails;
import com.example.toyproject.modules.user.controller.UserController;
import com.example.toyproject.modules.user.dto.UserModel;
import com.example.toyproject.modules.user.entity.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, UserModel> {
    public UserModelAssembler() {
        super(UserController.class, UserModel.class);
    }

    @Override
    public UserModel toModel(User user) {
        UserModel resource = new UserModel(user);
        resource.add(linkTo(methodOn(UserController.class).getUser(new CustomUserDetails(user), user.getId())).withSelfRel());
        return resource;
    }

    @Override
    public CollectionModel<UserModel> toCollectionModel(Iterable<? extends User> entities) {
        return super.toCollectionModel(entities);
    }
}
