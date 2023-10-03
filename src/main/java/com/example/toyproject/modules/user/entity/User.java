package com.example.toyproject.modules.user.entity;

import com.example.toyproject.modules.common.jpa.BaseTime;
import com.example.toyproject.modules.user.enums.RoleType;
import com.example.toyproject.modules.user.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTime {

    @Id
    @Comment("고유 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("아이디")
    private String username;

    @Comment("비밀번호")
    private String password;

    @Comment("유저 권한")
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Comment("유저 상태")
    @Enumerated(EnumType.STRING)
    private UserStatus status;
}
