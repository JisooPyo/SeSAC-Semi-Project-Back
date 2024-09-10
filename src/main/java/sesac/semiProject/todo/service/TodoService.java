package sesac.semiProject.todo.service;

import sesac.semiProject.todo.dto.TodoRequestDto;
import sesac.semiProject.todo.dto.TodoResponseDto;

public interface TodoService {
    /**
     * 정보를 받아 할 일을 추가합니다.
     * @param requestDto 할 일에 관한 정보입니다.
     * @return 등록된 할 일을 반환합니다.
     */
    TodoResponseDto createTodo(TodoRequestDto requestDto);
}
