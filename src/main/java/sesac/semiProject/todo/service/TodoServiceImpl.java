package sesac.semiProject.todo.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import sesac.semiProject.common.constants.TodoConstants;
import sesac.semiProject.common.dto.ApiResponseDto;
import sesac.semiProject.common.exception.CustomErrorCode;
import sesac.semiProject.common.exception.CustomException;
import sesac.semiProject.member.model.Member;
import sesac.semiProject.todo.dto.TodoRequestDto;
import sesac.semiProject.todo.dto.TodoResponseDto;
import sesac.semiProject.todo.model.Todo;
import sesac.semiProject.todo.repository.TodoRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;

    @Override
    public TodoResponseDto createTodo(TodoRequestDto requestDto, Member member) {
        Todo todo = requestDto.toEntity(member);
        Todo savedTodo = todoRepository.save(todo);
        return savedTodo.toDto();
    }

    @Override
    public List<TodoResponseDto> getAllTodos(Member member) {
        List<Todo> todos = todoRepository.findAllByMember_IdOrderByIdAsc(member.getId());
        return todos.stream().map(Todo::toDto).toList();
    }

    @Override
    public TodoResponseDto updateTodo(int id, TodoRequestDto requestDto) {
        Todo todo = findTodo(id);
        todo.update(requestDto);
        return todo.toDto();
    }

    @Override
    public ApiResponseDto deleteTodo(int id) {
        Todo todo = findTodo(id);
        todoRepository.deleteById(id);
        return new ApiResponseDto(HttpStatus.OK.value(), TodoConstants.DELETE_TODO_SUCCESS);
    }

    private Todo findTodo(int id) {
        return todoRepository.findById(id).orElseThrow(
            () -> new CustomException(CustomErrorCode.TODO_NOT_FOUND)
        );
    }
}
