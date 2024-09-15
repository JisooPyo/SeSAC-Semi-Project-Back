package sesac.semiProject.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import sesac.semiProject.common.dto.ApiResponseDto;
import sesac.semiProject.common.security.UserDetailsImpl;
import sesac.semiProject.member.dto.LoginRequestDto;
import sesac.semiProject.member.dto.SignupRequestDto;
import sesac.semiProject.member.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> signup(@RequestBody @Valid SignupRequestDto requestDto) {
        return ResponseEntity.ok().body(memberService.signup(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> login(
        @RequestBody LoginRequestDto requestDto,
        HttpServletResponse response) {
        return ResponseEntity.ok().body(memberService.login(requestDto, response));
    }

    @GetMapping("/nickname")
    public ResponseEntity<String> getNickname(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(userDetails.getMember().getNickname());
    }
}
