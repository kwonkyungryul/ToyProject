package com.example.toyproject.modules.parents.request;

import com.example.toyproject.modules.parents.entity.Parents;
import com.example.toyproject.modules.user.entity.User;
import com.example.toyproject.modules.user.request.UserSaveRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ParentsSaveRequest(

        @NotBlank(message = "이름을 입력해주세요.")
        String fullName,

        @NotBlank(message = "연락처를 입력해주세요.")
        String contact,

        @NotNull(message = "유저 정보를 입력해주세요.")
        UserSaveRequest request
) {

    public Parents toEntity(User user) {
        return new Parents(
                null,
                fullName,
                contact,
                user
        );
    }
}
