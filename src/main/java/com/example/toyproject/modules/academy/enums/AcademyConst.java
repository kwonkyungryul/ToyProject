package com.example.toyproject.modules.academy.enums;

import com.example.toyproject.modules.academy.entity.Academy;
import com.example.toyproject.modules.user.enums.UserConst;

public interface AcademyConst {

    String notFound = "학원 정보를 찾을 수 없습니다.";

    Academy academy = new Academy(1L, "권경렬", "윤선생영어교실 석포초점", "051-111-1111", "010-1111-1111", UserConst.academyUser, AcademyStatus.ACTIVE);
}