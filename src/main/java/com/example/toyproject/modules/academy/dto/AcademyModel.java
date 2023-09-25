package com.example.toyproject.modules.academy.dto;

import com.example.toyproject.modules.academy.entity.Academy;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.format.DateTimeFormatter;

@Relation(value = "academy", collectionRelation = "academies")
@Getter
@Setter
public class AcademyModel extends RepresentationModel<AcademyModel> {
    Long id;
    String fullName;
    String academyName;
    String contact;
    String phone;
    String username;
    String createDate;

    public AcademyModel(Academy academy) {
        this.id = academy.getId();
        this.fullName = academy.getFullName();
        this.academyName = academy.getAcademyName();
        this.contact = academy.getContact();
        this.phone = academy.getPhone();
        this.username = academy.getUsername();
        if (academy.getCreatedDate() == null ) {
            academy.changeCreatedDate(null);
        }
        this.createDate = academy.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
