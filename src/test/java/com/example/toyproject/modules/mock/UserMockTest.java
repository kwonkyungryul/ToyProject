package com.example.toyproject.modules.mock;

import com.example.toyproject.config.security.CustomUserDetails;
import com.example.toyproject.config.security.CustomUserDetailsService;
import com.example.toyproject.config.security.SecurityConfig;
import com.example.toyproject.config.security.SecurityTokenProvider;
import com.example.toyproject.modules.academy.controller.AcademyController;
import com.example.toyproject.modules.academy.service.AcademyService;
import com.example.toyproject.modules.user.controller.UserController;
import com.example.toyproject.modules.user.entity.User;
import com.example.toyproject.modules.user.enums.UserConst;
import com.example.toyproject.modules.user.service.UserService;
import com.example.toyproject.security.WithMockCustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import({SecurityConfig.class})
public class UserMockTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private SecurityTokenProvider tokenProvider;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("유저 상세 조회 실패 - 유저 없음")
    @WithMockCustomUser()
    void getUserFail() throws Exception {
        Long id = 0L;

        given(userService.getUser(id)).willReturn(
                Optional.empty()
        );

        ResultActions perform = mvc.perform(
                get("/users/{id}", id)
        );

        perform
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("유저 정보를 찾을 수 없습니다."));
    }

    @Test
    @DisplayName("유저 상세 조회 실패 - 권한 없음")
    @WithMockCustomUser()
    void getUserSuccess() throws Exception {
        Long id = 2L;
        User user = UserConst.academyUser;

        given(userService.getUser(id)).willReturn(
                Optional.of(
                        new User(
                                id,
                                user.getUsername(),
                                user.getPassword(),
                                user.getRole(),
                                user.getStatus()
                        )
                )
        );

        ResultActions perform = mvc.perform(
                get("/users/{id}", id)
        );

        perform
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.detail").value("권한이 없습니다."));
    }

    @Test
    @DisplayName("유저 상세 조회 성공")
    @WithMockCustomUser()
    void getUser() throws Exception {
        Long id = 1L;

        given(userService.getUser(id)).willReturn(
                Optional.of(
                        UserConst.academyUser
                )
        );

        ResultActions perform = mvc.perform(
                get("/users/{id}", id)
                        .accept("application/json")
        );

        perform
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("yoon"));
    }
}
