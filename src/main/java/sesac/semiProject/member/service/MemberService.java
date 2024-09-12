package sesac.semiProject.member.service;

import sesac.semiProject.common.dto.ApiResponseDto;
import sesac.semiProject.member.dto.SignupRequestDto;

public interface MemberService {
    /**
     * 정보를 받아 멤버 회원가입을 진행합니다.
     * @param requestDto 회원가입에 필요한 정보
     * @return 회원가입 성공 메시지
     */
    ApiResponseDto signup(SignupRequestDto requestDto);
}
