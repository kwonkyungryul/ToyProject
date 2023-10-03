package com.example.toyproject.modules.mock;

import com.example.toyproject.config.security.SecurityConfig;
import com.example.toyproject.config.security.SecurityTokenProvider;
import com.example.toyproject.modules.parents.controller.ParentsController;
import com.example.toyproject.modules.parents.enums.ParentsConst;
import com.example.toyproject.modules.parents.request.ParentsSaveRequest;
import com.example.toyproject.modules.parents.service.ParentsService;
import com.example.toyproject.modules.parents.service.RegisteredAcademyService;
import com.example.toyproject.modules.user.enums.UserConst;
import com.example.toyproject.modules.user.service.UserService;
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
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParentsController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(SecurityConfig.class)
public class ParentsMockTest {

    @MockBean
    private ParentsService parentsService;

    @MockBean
    private RegisteredAcademyService registeredAcademyService;

    @MockBean
    private UserService userService;

    @MockBean
    private SecurityTokenProvider tokenProvider;

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @DisplayName("학부모 회원가입 Valid 실패")
    void saveParentsValidFail() throws Exception {
        // given
        ParentsSaveRequest request = new ParentsSaveRequest("", "010-1313-1313", UserConst.saveRequest);

        // when
        ResultActions perform = mvc.perform(
                post("/parents/join")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)
                        )
        );

        perform
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("이름을 입력해주세요."))
        ;
    }

    @Test
    @DisplayName("학부모 회원가입 성공")
    void saveParents() throws Exception {
        // given
        ParentsSaveRequest request = new ParentsSaveRequest("권경렬", "010-1313-1313", UserConst.saveRequest);

        given(this.userService.save(request.request())).willReturn(
                UserConst.parentsUser
        );

        given(this.parentsService.save(request, UserConst.parentsUser)).willReturn(
                ParentsConst.parents
        );

        // when
        ResultActions perform = mvc.perform(
                post("/parents/join")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)
                        )
        );

        perform
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("권경렬"))
                .andExpect(jsonPath("$.contact").value("010-1111-1111"))
        ;
    }

    @Test
    @DisplayName("학부모 상세 조회 실패 - 학부모 정보 없음")
    void getParentsFail() throws Exception {
        // given
        Long id = 1L;
        given(this.parentsService.getParents(id)).willReturn(
                Optional.empty()
        );

        // when
        ResultActions perform = mvc.perform(
                get("/parents/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
        );

        perform
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value(ParentsConst.notFound))
        ;
    }

    @Test
    @DisplayName("학부모 상세 조회 성공")
    void getParents() throws Exception {
        // given
        Long id = 1L;
        given(this.parentsService.getParents(id)).willReturn(
                Optional.of(ParentsConst.parents)
        );

        // when
        ResultActions perform = mvc.perform(
                get("/parents/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
        );

        perform
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("권경렬"))
                .andExpect(jsonPath("$.contact").value("010-1111-1111"))
        ;
    }
}
