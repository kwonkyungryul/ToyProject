package com.example.toyproject.modules.parents.dto;

import com.example.toyproject.modules.academy.entity.Academy;
import com.example.toyproject.modules.parents.entity.Parents;
import com.example.toyproject.modules.user.UserModelAssembler;
import com.example.toyproject.modules.user.dto.UserModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.format.DateTimeFormatter;

@Relation(value = "academy", collectionRelation = "academies")
@Getter
@Setter
public class ParentsModel extends RepresentationModel<ParentsModel> {
    Long id;
    String fullName;
    String contact;
    String createDate;

    UserModel user;

    public ParentsModel(Parents parents) {
        this.id = parents.getId();
        this.fullName = parents.getFullName();
        this.contact = parents.getContact();

        this.user = new UserModelAssembler().toModel(parents.getUser());
        if (parents.getCreatedDate() == null ) {
            parents.changeCreatedDate(null);
        }
        this.createDate = parents.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
