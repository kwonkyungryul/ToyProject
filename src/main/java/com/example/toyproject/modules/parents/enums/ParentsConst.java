package com.example.toyproject.modules.parents.enums;

import com.example.toyproject.modules.parents.entity.Parents;
import com.example.toyproject.modules.user.enums.UserConst;

public interface ParentsConst {

    String notFound = "학부모 정보를 찾을 수 없습니다.";

    Parents parents = new Parents(1L, "권경렬", "010-1111-1111", UserConst.parentsUser);
}
