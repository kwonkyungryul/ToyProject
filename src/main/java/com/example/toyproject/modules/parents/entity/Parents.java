package com.example.toyproject.modules.parents.entity;

import com.example.toyproject.modules.common.jpa.BaseTime;
import com.example.toyproject.modules.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "parents")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Parents extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("학부모 이름")
    private String fullName;

    @Comment("학부모 연락처")
    private String contact;

    @Comment("유저 로그인 정보")
    @ManyToOne
    private User user;
}
