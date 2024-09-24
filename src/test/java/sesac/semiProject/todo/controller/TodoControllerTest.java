package sesac.semiProject.todo.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import sesac.semiProject.common.security.JwtUtil;
import sesac.semiProject.common.security.SecurityConfig;
import sesac.semiProject.common.security.UserDetailsImpl;
import sesac.semiProject.common.security.UserDetailsServiceImpl;
import sesac.semiProject.member.model.Member;
import sesac.semiProject.todo.dto.TodoRequestDto;
import sesac.semiProject.todo.dto.TodoResponseDto;
import sesac.semiProject.todo.service.TodoService;

@Import(SecurityConfig.class)
@WebMvcTest(value = TodoController.class)
class TodoControllerTest {
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
    private TodoService todoService;

    ObjectMapper objectMapper;

    int okStatus = HttpStatus.OK.value();
    String testMessage = "test-message";

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Member member = Member.builder()
            .email("test-member@email.com")
            .nickname("test-member")
            .password("encodedPassword")
            .build();
        userDetails = new UserDetailsImpl(member);
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(userDetails, null, null)
        );
    }

    @Test
    @DisplayName("createTodo: 성공")
    void createTodo() throws Exception {
        // given
        TodoRequestDto requestDto = TodoRequestDto.builder()
            .content("할일1")
            .dueDate(LocalDate.of(2024,9,24))
            .completed(false)
            .build();
        given(todoService.createTodo(any(TodoRequestDto.class), any(Member.class)))
            .willReturn(new TodoResponseDto());

        // when
        mockMvc.perform(post("/api/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("createTodo: 실패 - 내용이 없을 때")
    void createTodoFailureByEmptyContent() throws Exception {
        // given
        TodoRequestDto requestDto = TodoRequestDto.builder()
            .content("")
            .dueDate(LocalDate.of(2024,9,24))
            .completed(false)
            .build();

        // when
        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getAllTodos() {
    }

    @Test
    void updateTodo() {
    }

    @Test
    void deleteTodo() {
    }
}
