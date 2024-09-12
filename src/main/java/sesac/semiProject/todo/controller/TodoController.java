package sesac.semiProject.todo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import sesac.semiProject.common.dto.ApiResponseDto;
import sesac.semiProject.common.security.UserDetailsImpl;
import sesac.semiProject.todo.dto.TodoRequestDto;
import sesac.semiProject.todo.dto.TodoResponseDto;
import sesac.semiProject.todo.service.TodoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TodoController {
    private final TodoService todoService;

    @PostMapping("/todos")
    public ResponseEntity<TodoResponseDto> createTodo(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody @Valid TodoRequestDto requestDto
    ) {
        return ResponseEntity.ok().body(todoService.createTodo(requestDto, userDetails.getMember()));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<TodoResponseDto>> getAllTodos(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(todoService.getAllTodos(userDetails.getMember()));
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(
        @PathVariable int id,
        @RequestBody @Valid TodoRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok().body(todoService.updateTodo(id, requestDto, userDetails.getMember()));
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<ApiResponseDto> deleteTodo(
        @PathVariable int id,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok().body(todoService.deleteTodo(id, userDetails.getMember()));
    }
}
