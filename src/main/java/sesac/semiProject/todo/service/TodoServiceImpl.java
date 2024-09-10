package sesac.semiProject.todo.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sesac.semiProject.todo.dto.TodoRequestDto;
import sesac.semiProject.todo.dto.TodoResponseDto;
import sesac.semiProject.todo.model.Todo;
import sesac.semiProject.todo.repository.TodoRepository;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;

    @Override
    public TodoResponseDto createTodo(TodoRequestDto requestDto) {
        Todo todo = requestDto.toEntity();
        Todo savedTodo = todoRepository.save(todo);
        return savedTodo.toDto();
    }
}
