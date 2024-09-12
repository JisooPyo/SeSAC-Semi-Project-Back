package sesac.semiProject.member.service;

import jakarta.servlet.http.HttpServletResponse;
import sesac.semiProject.common.dto.ApiResponseDto;
import sesac.semiProject.member.dto.LoginRequestDto;
import sesac.semiProject.member.dto.SignupRequestDto;

public interface MemberService {
    /**
     * 정보를 받아 멤버 회원가입을 진행합니다.
     * @param requestDto 회원가입에 필요한 정보
     * @return 회원가입 성공 메시지
     */
    ApiResponseDto signup(SignupRequestDto requestDto);

    /**
     * 정보를 받아 멤버 로그인을 진행합니다.
     * @param requestDto 로그인에 필요한 정보
     * @param response 응답 헤더에 토큰을 보냅니다.
     * @return 로그인 성공 여부 메시지
     */
    ApiResponseDto login(LoginRequestDto requestDto, HttpServletResponse response);
}
