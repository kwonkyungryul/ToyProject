package com.example.toyproject.modules.academy.entity;

import com.example.toyproject.modules.academy.enums.AcademyStatus;
import com.example.toyproject.modules.common.jpa.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ACADEMIES")
public class Academy extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유 번호")
    private Long id;

    @Comment("학원장 이름")
    private String fullName;

    @Comment("학원 이름")
    private String academyName;

    @Comment("학원 전화번호")
    private String contact;

    @Comment("학원장 연락처")
    private String phone;

    @Comment("아이디")
    private String username;

    @Comment("비밀번호")
    private String password;

    // TODO 엔티티로 뺄 예정
//    @Comment("학원 FCM 토큰")
//    private String fcmToken;

    @Comment("학원 활성화 상태")
    @Enumerated(EnumType.STRING)
    private AcademyStatus status;
}
