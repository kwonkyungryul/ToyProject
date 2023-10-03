package com.example.toyproject.modules.parents.entity;


import com.example.toyproject.modules.academy.entity.Academy;
import com.example.toyproject.modules.common.jpa.BaseTime;
import com.example.toyproject.modules.parents.enums.RegisteredStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "REGISTERED_ACADEMIES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredAcademy extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유 번호")
    private Long id;

    @ManyToOne
    @Comment("학부모 정보")
    private Parents parents;

    @ManyToOne
    @Comment("학원 정보")
    private Academy academy;

    @Comment("등록 상태")
    @Enumerated(EnumType.STRING)
    private RegisteredStatus status;
}
