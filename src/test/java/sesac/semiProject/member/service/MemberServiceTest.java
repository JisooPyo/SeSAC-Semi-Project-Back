package sesac.semiProject.member.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.servlet.http.HttpServletResponse;
import sesac.semiProject.common.constants.MemberConstants;
import sesac.semiProject.common.dto.ApiResponseDto;
import sesac.semiProject.common.exception.CustomException;
import sesac.semiProject.common.security.JwtUtil;
import sesac.semiProject.member.dto.LoginRequestDto;
import sesac.semiProject.member.dto.SignupRequestDto;
import sesac.semiProject.member.model.Member;
import sesac.semiProject.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    MemberRepository memberRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtUtil jwtUtil;

    @Mock
    HttpServletResponse response;

    @InjectMocks
    MemberServiceImpl memberService;

    String testEmail = "test-member@email.com";
    String testPassword = "password123!";
    String testNickname = "testNickname";

    SignupRequestDto signupRequestDto = SignupRequestDto.builder()
        .email(testEmail)
        .password(testPassword)
        .nickname(testNickname)
        .build();

    LoginRequestDto loginRequestDto = LoginRequestDto.builder()
        .email(testEmail)
        .password(testPassword)
        .build();

    @Test
    @DisplayName("signup: 성공")
    void signup() {
        // given
        given(memberRepository.findByEmail(anyString())).willReturn(Optional.empty());
        given(passwordEncoder.encode(anyString())).willReturn("encodedPassword");

        // when
        ApiResponseDto responseDto = memberService.signup(signupRequestDto);

        // then
        then(memberRepository).should().save(any(Member.class));
        assertEquals(HttpStatus.OK.value(), responseDto.getStatusCode());
        assertEquals(MemberConstants.SIGNUP_SUCCESS, responseDto.getMessage());
    }

    @Test
    @DisplayName("signup: 실패 - 이미 가입한 계정이 있는 경우")
    void signupFailureByAccountExists() {
        // given
        Member member = new Member();
        given(memberRepository.findByEmail(anyString())).willReturn(Optional.of(member));

        // when
        assertThrows(
            CustomException.class,
            () -> memberService.signup(signupRequestDto)
        );
    }

    @Test()
    @DisplayName("login: 성공")
    void login() {
        // given
        given(jwtUtil.createToken(anyString())).willReturn("token");
        Member member = Member.builder().password("temp-password").build();
        given(memberRepository.findByEmail(anyString())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);

        // when
        ApiResponseDto responseDto = memberService.login(loginRequestDto, response);

        // then
        then(response).should().setHeader(anyString(), anyString());
        assertEquals(HttpStatus.OK.value(), responseDto.getStatusCode());
        assertEquals(MemberConstants.LOGIN_SUCCESS, responseDto.getMessage());
    }

    @Test
    @DisplayName("login: 실패 - 계정이 존재하지 않는 경우")
    void loginFailureByAccountNotExists() {
        // given
        given(memberRepository.findByEmail(anyString())).willReturn(Optional.empty());

        // when
        assertThrows(
            CustomException.class,
            () -> memberService.login(loginRequestDto, response)
        );
    }

    @Test
    @DisplayName("login: 실패 - 비밀번호가 일치하지 않는 경우")
    void loginFailureByPasswordNotMatch() {
        // given
        Member member = Member.builder().password("temp-password").build();
        given(memberRepository.findByEmail(anyString())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        // when
        assertThrows(
            CustomException.class,
            () -> memberService.login(loginRequestDto, response)
        );
    }
}
