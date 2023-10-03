package com.example.toyproject.modules.user.enums;

import com.example.toyproject.modules.user.entity.User;
import com.example.toyproject.modules.user.request.UserSaveRequest;

public interface UserConst {

    String notFound = "유저 정보를 찾을 수 없습니다.";
    User academyUser = new User(1L, "yoon", "1234", RoleType.ACADEMY, UserStatus.ACTIVE);
    User parentsUser = new User(1L, "yoon", "1234", RoleType.PARENTS, UserStatus.ACTIVE);
    User studentUser = new User(1L, "yoon", "1234", RoleType.STUDENT, UserStatus.ACTIVE);

    UserSaveRequest saveRequest = new UserSaveRequest("yoon", "1234");

}
