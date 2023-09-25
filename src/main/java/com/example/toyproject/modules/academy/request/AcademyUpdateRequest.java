package com.example.toyproject.modules.academy.request;

import com.example.toyproject.modules.academy.enums.AcademyStatus;
import com.example.toyproject.modules.common.custom_annotation.ValueOfEnum;
import jakarta.validation.constraints.NotBlank;

public record AcademyUpdateRequest(
        @NotBlank(message = "이름을 입력해주세요.")
        String fullName,

        @NotBlank(message = "학원명을 입력해주세요.")
        String academyName,

        @NotBlank(message = "학원 전화번호를 입력해주세요.")
        String contact,

        @NotBlank(message = "학원장 연락처를 입력해주세요.")
        String phone,

        @ValueOfEnum(enumClass = AcademyStatus.class, message = "학원 상태값에 이상이 있습니다.")
        String status
) {
}
