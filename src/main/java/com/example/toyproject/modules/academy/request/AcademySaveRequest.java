package com.example.toyproject.modules.academy.request;

import com.example.toyproject.modules.academy.entity.Academy;
import com.example.toyproject.modules.academy.enums.AcademyStatus;
import jakarta.validation.constraints.NotBlank;

public record AcademySaveRequest(

        @NotBlank(message = "이름을 입력해주세요.")
        String fullName,

        @NotBlank(message = "학원명을 입력해주세요.")
        String academyName,

        @NotBlank(message = "학원 전화번호를 입력해주세요.")
        String contact,

        @NotBlank(message = "학원장 연락처를 입력해주세요.")
        String phone,

        @NotBlank(message = "아이디를 입력해주세요.")
        String username,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password
) {

        public Academy toEntity() {
                return new Academy(
                        null,
                        fullName,
                        academyName,
                        contact,
                        phone,
                        username,
                        password,
                        AcademyStatus.valueOf("ACTIVE")
                );
        }
}
