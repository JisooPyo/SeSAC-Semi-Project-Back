package sesac.semiProject.todo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import sesac.semiProject.common.constants.TodoConstants;
import sesac.semiProject.common.dto.ApiResponseDto;
import sesac.semiProject.common.exception.CustomException;
import sesac.semiProject.member.model.Member;
import sesac.semiProject.todo.dto.TodoRequestDto;
import sesac.semiProject.todo.dto.TodoResponseDto;
import sesac.semiProject.todo.model.Todo;
import sesac.semiProject.todo.repository.TodoRepository;

@ExtendWith(MockitoExtension.class)
class TodoServiceImplTest {
    @Mock
    TodoRepository todoRepository;

    @InjectMocks
    TodoServiceImpl todoService;

    Member member = Member.builder()
        .id(5)
        .build();

    @Test
    @DisplayName("createTodo: 성공")
    void createTodo() {
        String content = "할일 1";
        LocalDate dueDate = LocalDate.of(2024, 9, 24);
        boolean completed = false;

        TodoRequestDto requestDto = TodoRequestDto.builder()
            .content(content)
            .dueDate(dueDate)
            .completed(completed)
            .build();

        // given
        int id = 3;
        Todo todo = Todo.builder()
            .id(id)
            .content(content)
            .dueDate(dueDate)
            .completed(completed)
            .build();
        given(todoRepository.save(any(Todo.class))).willReturn(todo);

        // when
        TodoResponseDto responseDto = todoService.createTodo(requestDto, member);

        // then
        then(todoRepository).should().save(any(Todo.class));
        assertEquals(id, responseDto.getId());
        assertEquals(content, responseDto.getContent());
        assertEquals(dueDate, responseDto.getDueDate());
        assertEquals(completed, responseDto.getCompleted());
        then(todoRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("getAllTodos: 성공")
    void getAllTodos() {
        // given
        List<Todo> todoList = new ArrayList<>();
        int size = 5;
        for (int i = 0; i < size; i++) {
            todoList.add(new Todo());
        }
        given(todoRepository.findAllByMember_IdOrderByIdAsc(anyInt())).willReturn(todoList);

        // when
        List<TodoResponseDto> dtoList = todoService.getAllTodos(member);

        // then
        assertEquals(size, dtoList.size());
        then(todoRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("updateTodo: 성공")
    void updateTodo() {
        // given
        String updateContent = "할일1_수정";
        LocalDate updatedDueDate = LocalDate.of(2024, 9, 25);
        boolean updatedCompleted = true;
        TodoRequestDto requestDto = TodoRequestDto.builder()
            .content(updateContent)
            .dueDate(updatedDueDate)
            .completed(updatedCompleted)
            .build();

        int id = 3;
        Todo todo = Todo.builder()
            .id(id)
            .member(member)
            .build();
        given(todoRepository.findById(anyInt())).willReturn(Optional.of(todo));

        // when
        TodoResponseDto responseDto = todoService.updateTodo(id, requestDto, member);

        // then
        assertEquals(id, responseDto.getId());
        assertEquals(updateContent, responseDto.getContent());
        assertEquals(updatedDueDate, responseDto.getDueDate());
        assertEquals(updatedCompleted, responseDto.getCompleted());
    }

    @Test
    @DisplayName("updateTodo: 실패 - 존재하지 않는 Todo")
    void updateTodoFailureByNotExistingTodo() {
        // given
        TodoRequestDto requestDto = TodoRequestDto.builder().build();

        int id = 3;
        given(todoRepository.findById(anyInt())).willReturn(Optional.empty());

        // when
        assertThrows(
            CustomException.class,
            () -> todoService.updateTodo(id, requestDto, member)
        );
    }

    @Test
    @DisplayName("updateTodo: 실패 - 권한이 없을 때")
    void updateTodoFailureByNotAuthorized() {
        // given
        TodoRequestDto requestDto = TodoRequestDto.builder().build();

        int id = 3;
        Todo todo = Todo.builder()
            .id(id)
            .member(Member.builder().id(100).build())
            .build();
        given(todoRepository.findById(anyInt())).willReturn(Optional.of(todo));

        // when
        assertThrows(
            CustomException.class,
            () -> todoService.updateTodo(id, requestDto, member)
        );
    }

    @Test
    @DisplayName("deleteTodo: 성공")
    void deleteTodo() {
        // given
        int id = 3;
        Todo todo = Todo.builder()
            .id(id)
            .member(member)
            .build();
        given(todoRepository.findById(anyInt())).willReturn(Optional.of(todo));

        // when
        ApiResponseDto apiResponseDto = todoService.deleteTodo(id, member);

        // then
        then(todoRepository).should().deleteById(anyInt());
        then(todoRepository).shouldHaveNoMoreInteractions();
        assertEquals(HttpStatus.OK.value(), apiResponseDto.getStatusCode());
        assertEquals(TodoConstants.DELETE_TODO_SUCCESS, apiResponseDto.getMessage());
    }
}
