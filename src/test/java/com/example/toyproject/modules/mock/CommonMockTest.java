package com.example.toyproject.modules.mock;


import com.example.toyproject.config.security.SecurityConfig;
import com.example.toyproject.config.security.SecurityTokenProvider;
import com.example.toyproject.modules.common.controller.CommonController;
import com.example.toyproject.modules.common.request.LoginRequest;
import com.example.toyproject.modules.common.service.CommonService;
import com.example.toyproject.modules.user.enums.UserConst;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommonController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(SecurityConfig.class)
public class CommonMockTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    CommonService commonService;

    @MockBean
    SecurityTokenProvider tokenProvider;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("로그인 실패 - Valid 실패")
    void loginValidFail() throws Exception {
        //given
        LoginRequest request = new LoginRequest(
                "",
                "password"
        );

        //when
        //then
        mvc.perform(
                post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        request
                                )
                        )
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("아이디를 입력해주세요."));
    }

    @Test
    @DisplayName("로그인 실패 - 아이디 없음")
    void loginNotFoundUser() throws Exception {
        //given
        LoginRequest request = new LoginRequest(
                "username",
                "password"
        );

        //when
        given(commonService.findUser(request)).willReturn(
                Optional.empty()
        );

        //then
        mvc.perform(
                post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        request
                                )
                        )
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("ID 혹은 Password가 잘못되었습니다."));
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 불일치")
    void loginIncorrectPassword() throws Exception {
        //given
        LoginRequest request = new LoginRequest(
                "username",
                "password"
        );

        //when
        given(commonService.findUser(request)).willReturn(
                Optional.of(UserConst.academyUser)
        );

        given(commonService.passwordCheck(UserConst.academyUser.getPassword(), request.password())).willReturn(
                false
        );

        //then
        mvc.perform(
                        post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                request
                                        )
                                )
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("ID 혹은 Password가 잘못되었습니다."));
    }

    @Test
    @DisplayName("로그인 성공")
    void login() throws Exception {
        //given
        LoginRequest request = new LoginRequest(
                "username",
                "password"
        );

        //when
        given(commonService.findUser(request)).willReturn(
                Optional.of(UserConst.academyUser)
        );

        given(commonService.passwordCheck(UserConst.academyUser.getPassword(), request.password())).willReturn(
                true
        );

        given(commonService.getToken(UserConst.academyUser)).willReturn(
                "jwtToken"
        );

        //then
        mvc.perform(
                        post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                request
                                        )
                                )
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("로그인이 완료되었습니다."));
    }
}
