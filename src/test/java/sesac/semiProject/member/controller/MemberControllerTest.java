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
import sesac.semiProject.member.dto.SignupRequestDto;
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

    ObjectMapper objectMapper;

    String testEmail = "test-user@email.com";
    String testPassword = "password123!";
    String testNickname = "testUser";
    int okStatus = HttpStatus.OK.value();
    String testMessage = "test-message";

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        userDetails = Mockito.mock(UserDetailsImpl.class);
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(userDetails, null, null)
        );
    }

    @Test
    @DisplayName("멤버 닉네임 가져오기: 성공")
    void getNickname() throws Exception {
        // given
        Member member = Member.builder().nickname(testNickname).build();
        given(userDetails.getMember()).willReturn(member);

        // when
        mockMvc.perform(get("/api/members/nickname"))
            .andExpect(status().isOk())
            .andExpect(content().string(testNickname));
    }

    @Test
    @DisplayName("로그인: 성공")
    void login() throws Exception {
        // given
        LoginRequestDto loginRequestDto =
            LoginRequestDto.builder()
                .email(testEmail)
                .password(testPassword)
                .build();
        ApiResponseDto responseDto = new ApiResponseDto(okStatus, testMessage);
        given(memberService.login(any(LoginRequestDto.class), any(HttpServletResponse.class)))
            .willReturn(responseDto);

        // when
        mockMvc.perform(post("/api/members/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.statusCode").value(okStatus))
            .andExpect(jsonPath("$.message").value(testMessage));
    }

    @Test
    @DisplayName("회원가입: 성공")
    void signup() throws Exception {
        // given
        SignupRequestDto signupRequestDto =
            SignupRequestDto.builder()
                .email(testEmail)
                .password(testPassword)
                .nickname(testNickname)
                .build();
        ApiResponseDto responseDto = new ApiResponseDto(okStatus, testMessage);
        given(memberService.signup(any(SignupRequestDto.class))).willReturn(responseDto);

        // when
        mockMvc.perform(post("/api/members/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.statusCode").value(okStatus))
            .andExpect(jsonPath("$.message").value(testMessage));
    }

    @Test
    @DisplayName("회원가입: 실패 - 이메일 형식이 올바르지 않을 때")
    void signupFailureByInvalidEmail() throws Exception {
        // given
        SignupRequestDto signupRequestDto =
            SignupRequestDto.builder()
                .email("invalid email")
                .password(testPassword)
                .nickname(testNickname)
                .build();

        // when
        mockMvc.perform(post("/api/members/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequestDto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입: 실패 - 비밀번호가 8자 미만일 때")
    void signupFailureByTooShortPassword() throws Exception {
        // given
        SignupRequestDto signupRequestDto =
            SignupRequestDto.builder()
                .email(testEmail)
                .password("pas123!")
                .nickname(testNickname)
                .build();

        // when
        mockMvc.perform(post("/api/members/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequestDto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입: 실패 - 비밀번호 규칙(영소문자, 숫자, 특수문자 1개 이상 포함)에 맞지 않을 때")
    void signupFailureByInvalidPassword() throws Exception {
        // given
        SignupRequestDto signupRequestDto =
            SignupRequestDto.builder()
                .email(testEmail)
                .password("password123")
                .nickname(testNickname)
                .build();

        // when
        mockMvc.perform(post("/api/members/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequestDto)))
            .andExpect(status().isBadRequest());
    }

    // todo
    @Test
    @DisplayName("회원가입: 실패 - 닉네임이 빈 칸으로 들어왔을 때")
    void signupFailureByEmptyNickname() throws Exception {
        // given
        SignupRequestDto signupRequestDto =
            SignupRequestDto.builder()
                .email(testEmail)
                .password(testPassword)
                .nickname("")
                .build();

        // when
        mockMvc.perform(post("/api/members/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequestDto)))
            .andExpect(status().isBadRequest());
    }

    // todo
    @Test
    @DisplayName("회원가입: 실패 - 닉네임이 null 로 들어올 때")
    void signupFailureByNullNickname() throws Exception {
        // given
        SignupRequestDto signupRequestDto =
            SignupRequestDto.builder()
                .email(testEmail)
                .password(testPassword)
                .build();

        // when
        mockMvc.perform(post("/api/members/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequestDto)))
            .andExpect(status().isBadRequest());
    }
}
