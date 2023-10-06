package com.example.toyproject.modules.integrated.controller;

import com.example.toyproject.modules.common.request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@DisplayName("공통 통합 테스트")
public class CommonControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    @DisplayName("로그인 실패 - 아이디가 없음")
    void signInFailUsernameNotFound() throws Exception {

        LoginRequest request = new LoginRequest(
                "",
                "1234"
        );

        ResultActions perform = mockMvc.perform(
                post("/login")
                        .accept(MediaTypes.HAL_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        perform
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(document("login-valid-fail", responseFields(
                                fieldWithPath("type").description("참조 문서"),
                                fieldWithPath("title").description("에러 이름"),
                                fieldWithPath("status").description("에러 상태 코드"),
                                fieldWithPath("detail").description("에러 내용"),
                                fieldWithPath("instance").description("요청한 주소")
                        )
                ));
    }

    @Test
    @DisplayName("로그인 실패 - 아이디 또는 비밀번호 불일치")
    void signInFail() throws Exception {

        LoginRequest request = new LoginRequest(
                "yoon",
                "123413131"
        );

        ResultActions perform = mockMvc.perform(
                post("/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        perform
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(document("login-mismatch-fail", responseFields(
                                fieldWithPath("type").description("참조 문서"),
                                fieldWithPath("title").description("에러 이름"),
                                fieldWithPath("status").description("에러 상태 코드"),
                                fieldWithPath("detail").description("에러 내용"),
                                fieldWithPath("instance").description("요청한 주소")
                        )
                ));
    }


    @Test
    @DisplayName("로그인 성공")
    void signIn() throws Exception {

        LoginRequest request = new LoginRequest(
                "yoon",
                "1234"
        );

        ResultActions perform = mockMvc.perform(
                post("/login")
                        .accept(MediaTypes.HAL_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        perform
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("login-success",
                        requestFields(
                                fieldWithPath("username")
                                        .type(JsonFieldType.STRING)
                                        .description("아이디"),
                                fieldWithPath("password")
                                        .type(JsonFieldType.STRING)
                                        .description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("message").description("로그인 성공 메세지")
                        ),
                        responseHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        )
                ));
    }
}
