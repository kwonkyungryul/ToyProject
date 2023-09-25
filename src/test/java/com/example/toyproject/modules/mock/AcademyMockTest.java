package com.example.toyproject.modules.mock;

import com.example.toyproject.config.SecurityConfig;
import com.example.toyproject.modules.academy.controller.AcademyController;
import com.example.toyproject.modules.academy.enums.AcademyConst;
import com.example.toyproject.modules.academy.request.AcademySaveRequest;
import com.example.toyproject.modules.academy.service.AcademyService;
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

@WebMvcTest(AcademyController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(SecurityConfig.class)
public class AcademyMockTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AcademyService academyService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("학원 회원가입 Valid 실패")
    void saveAcademyValidFail() throws Exception {
        AcademySaveRequest request = new AcademySaveRequest(
                "",
                "윤선생영어교실 석포초점",
                "051-111-1111",
                "010-1111-1111",
                "kkr",
                "1234"
        );

        ResultActions perform = this.mvc.perform(
                post("/academies/join")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.detail").value("이름을 입력해주세요."));
    }

    @Test
    @DisplayName("학원 회원가입 성공")
    void saveAcademySuccess() throws Exception {
        AcademySaveRequest request = new AcademySaveRequest(
                "권경렬",
                "윤선생영어교실 석포초점",
                "051-111-1111",
                "010-1111-1111",
                "kkr",
                "1234"
        );

        given(this.academyService.save(request)).willReturn(
                AcademyConst.academy
        );

        ResultActions perform = this.mvc.perform(
                post("/academies/join")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
        );

        perform
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.fullName").value("권경렬"))
                .andExpect(jsonPath("$.academyName").value("윤선생영어교실 석포초점"))
                .andExpect(jsonPath("$.contact").value("051-111-1111"))
                .andExpect(jsonPath("$.phone").value("010-1111-1111"))
                .andExpect(jsonPath("$.username").value("kkr"))
        ;
    }

    @Test
    @DisplayName("학원 상세 조회 Valid 실패")
    void getAcademyValidFail() throws Exception {
        Long id = 0L;

        given(this.academyService.getAcademy(id)).willReturn(
                Optional.empty()
        );

        ResultActions perform = this.mvc.perform(
                get("/academies/{id}", id)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.detail").value("학원 정보를 찾을 수 없습니다."));
    }

    @Test
    @DisplayName("학원 상세 조회 성공")
    void getAcademySuccess() throws Exception {
        Long id = 1L;

        given(this.academyService.getAcademy(id)).willReturn(
                Optional.of(AcademyConst.academy)
        );

        ResultActions perform = this.mvc.perform(
                get("/academies/{id}", id)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.fullName").value("권경렬"))
                .andExpect(jsonPath("$.academyName").value("윤선생영어교실 석포초점"))
                .andExpect(jsonPath("$.contact").value("051-111-1111"))
                .andExpect(jsonPath("$.phone").value("010-1111-1111"))
                .andExpect(jsonPath("$.username").value("kkr"))
        ;
    }
}
