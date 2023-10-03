package com.example.toyproject.modules.academy.request;

import com.example.toyproject.modules.academy.entity.Academy;
import com.example.toyproject.modules.academy.enums.AcademyStatus;
import com.example.toyproject.modules.user.entity.User;
import com.example.toyproject.modules.user.request.UserSaveRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AcademySaveRequest(

        @NotBlank(message = "이름을 입력해주세요.")
        String fullName,

        @NotBlank(message = "학원명을 입력해주세요.")
        String academyName,

        @NotBlank(message = "학원 전화번호를 입력해주세요.")
        String contact,

        @NotBlank(message = "학원장 연락처를 입력해주세요.")
        String phone,

        @NotNull(message = "유저 정보를 입력해주세요.")
        UserSaveRequest request
) {

        public Academy toEntity(User user) {
                return new Academy(
                        null,
                        fullName,
                        academyName,
                        contact,
                        phone,
                        user,
                        null,
                        AcademyStatus.valueOf("ACTIVE")
                );
        }
}
