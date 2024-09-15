package sesac.semiProject.member.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import sesac.semiProject.common.security.UserDetailsImpl;
import sesac.semiProject.member.model.Member;
import sesac.semiProject.member.service.MemberService;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

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

        mockMvc.perform(get("/api/members/nickname"))
            .andExpect(status().isOk())
            .andExpect(content().string(nickname));
    }
}
