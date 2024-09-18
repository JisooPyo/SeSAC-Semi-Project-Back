package sesac.semiProject.member.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import sesac.semiProject.common.dto.ApiResponseDto;
import sesac.semiProject.common.security.JwtUtil;
import sesac.semiProject.common.security.SecurityConfig;
import sesac.semiProject.common.security.UserDetailsImpl;
import sesac.semiProject.common.security.UserDetailsServiceImpl;
import sesac.semiProject.member.dto.LoginRequestDto;
import sesac.semiProject.member.model.Member;
import sesac.semiProject.member.service.MemberService;

@Import(SecurityConfig.class)
@WebMvcTest(value = MemberController.class)
class MemberControllerTest {
    // SecurityConfig 의존성 관리
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    // 테스트 코드 관련 의존성
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsImpl userDetails;

    @MockBean
    private MemberService memberService;

    String nickname = "testUser";

    @BeforeEach
    void setUp() {
        userDetails = Mockito.mock(UserDetailsImpl.class);
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(userDetails, null, null)
        );
    }

    @Test
    @WithMockUser
    void getNickname() throws Exception {
        // given
        Member member = Member.builder().nickname(nickname).build();
        given(userDetails.getMember()).willReturn(member);

        // when
        mockMvc.perform(get("/api/members/nickname"))
            .andExpect(status().isOk())
            .andExpect(content().string(nickname));
    }

    @Test
    void login() throws Exception {
        // given
        LoginRequestDto loginRequestDto =
            LoginRequestDto.builder()
                .email("test-user@email.com")
                .password("test-password")
                .build();
        int expectedStatusCode = HttpStatus.OK.value();
        String expectedMessage = "test-message";
        ApiResponseDto responseDto = new ApiResponseDto(expectedStatusCode, expectedMessage);
        given(memberService.login(any(LoginRequestDto.class), any(HttpServletResponse.class)))
            .willReturn(responseDto);

        // when
        mockMvc.perform(post("/api/members/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.statusCode").value(expectedStatusCode))
            .andExpect(jsonPath("$.message").value(expectedMessage));
    }
}
