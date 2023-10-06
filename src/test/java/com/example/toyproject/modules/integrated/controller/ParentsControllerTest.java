package com.example.toyproject.modules.integrated.controller;

import com.example.toyproject.modules.parents.request.ParentsSaveRequest;
import com.example.toyproject.modules.user.request.UserSaveRequest;
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

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@DisplayName("학부모 통합 테스트")
public class ParentsControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    @DisplayName("학부모 회원가입 실패 - 학부모 이름이 없음")
    void parentsJoinFailUsernameNotFound() throws Exception {
        ParentsSaveRequest request = new ParentsSaveRequest(
                "",
                "윤선생영어교실 석포초점",
                new UserSaveRequest("yoon", "1234"));

        ResultActions perform = this.mockMvc.perform(
                post("/parents/join")
                        .accept(MediaTypes.HAL_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        perform
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(document("parents-join-fail", responseFields(
                                fieldWithPath("type").description("참조 문서"),
                                fieldWithPath("title").description("에러 이름"),
                                fieldWithPath("status").description("에러 상태 코드"),
                                fieldWithPath("detail").description("에러 내용"),
                                fieldWithPath("instance").description("요청한 주소")
                        )
                ));
    }

    @Test
    @DisplayName("학부모 회원가입 성공")
    void parentsJoin() throws Exception {
        UserSaveRequest userSaveRequest = new UserSaveRequest("yoon5", "1234");

        ParentsSaveRequest request = new ParentsSaveRequest(
                "권호현",
                "윤선생영어교실 석포초점",
                userSaveRequest
        );


        ResultActions perform = this.mockMvc.perform(
                post("/parents/join")
                        .accept(MediaTypes.HAL_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        perform
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("parents-join-success",
                        requestFields(
                                fieldWithPath("fullName").type(JsonFieldType.STRING).description("학부모 이름"),
                                fieldWithPath("contact").type(JsonFieldType.STRING).description("학원 이름"),
                                fieldWithPath("request.username").type(JsonFieldType.STRING).description("학원 연락처"),
                                fieldWithPath("request.password").type(JsonFieldType.STRING).description("학원장 연락처")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("학부모 아이디"),
                                fieldWithPath("fullName").type(JsonFieldType.STRING).description("학부모 이름"),
                                fieldWithPath("contact").type(JsonFieldType.STRING).description("학부모 연락처"),
                                fieldWithPath("createDate").type(JsonFieldType.STRING).description("회원 정보"),
                                fieldWithPath("user.id").type(JsonFieldType.NUMBER).description("유저 아이디"),
                                fieldWithPath("user.username").type(JsonFieldType.STRING).description("유저 비밀번호"),
                                fieldWithPath("user.createDate").type(JsonFieldType.STRING).description("유저 생성일"),
                                fieldWithPath("user._links.self.href").type(JsonFieldType.STRING).description("관리자 권한"),
                                subsectionWithPath("_links.self.href").type(JsonFieldType.STRING).description("학원 상세 링크")
                        ),
                        links(
                                linkWithRel("self").description("현재 페이지 링크")
                        )
                ));
    }
}
