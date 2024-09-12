package sesac.semiProject.member.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import sesac.semiProject.common.constants.MemberConstants;
import sesac.semiProject.common.dto.ApiResponseDto;
import sesac.semiProject.common.exception.CustomErrorCode;
import sesac.semiProject.common.exception.CustomException;
import sesac.semiProject.common.security.JwtUtil;
import sesac.semiProject.member.dto.LoginRequestDto;
import sesac.semiProject.member.dto.SignupRequestDto;
import sesac.semiProject.member.model.Member;
import sesac.semiProject.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public ApiResponseDto signup(SignupRequestDto requestDto) {
        if (findMember(requestDto.getEmail()) != null) {
            throw new CustomException(CustomErrorCode.ALREADY_ACCOUNT_EXISTS);
        }
        Member member = Member.builder()
            .email(requestDto.getEmail())
            .password(passwordEncoder.encode(requestDto.getPassword()))
            .nickname(requestDto.getNickname())
            .build();
        memberRepository.save(member);
        return new ApiResponseDto(HttpStatus.OK.value(), MemberConstants.SIGNUP_SUCCESS);
    }

    @Override
    public ApiResponseDto login(LoginRequestDto requestDto, HttpServletResponse response) {
        checkAccount(requestDto);
        String token = jwtUtil.createToken(requestDto.getEmail());
        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, token);
        return new ApiResponseDto(HttpStatus.OK.value(), MemberConstants.LOGIN_SUCCESS);
    }

    private Member findMember(String email) {
        return memberRepository.findByEmail(email).orElse(null);
    }

    private void checkAccount(LoginRequestDto requestDto) {
        Member member = findMember(requestDto.getEmail());
        if (member == null
            || !passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new CustomException(CustomErrorCode.INVALID_ACCESS);
        }
    }
}
