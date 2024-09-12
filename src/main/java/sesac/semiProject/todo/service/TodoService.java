package sesac.semiProject.todo.service;

import java.util.List;

import sesac.semiProject.common.dto.ApiResponseDto;
import sesac.semiProject.member.model.Member;
import sesac.semiProject.todo.dto.TodoRequestDto;
import sesac.semiProject.todo.dto.TodoResponseDto;

public interface TodoService {
    /**
     * 정보를 받아 할 일을 추가합니다.
     *
     * @param requestDto 할 일에 관한 정보입니다.
     * @param member 할 일을 등록하는 유저입니다.
     * @return 등록된 할 일을 반환합니다.
     */
    TodoResponseDto createTodo(TodoRequestDto requestDto, Member member);

    /**
     * 모든 할 일 목록을 가져옵니다.
     * @param member 요청한 멤버입니다.
     * @return 모든 할 일 목록
     */
    List<TodoResponseDto> getAllTodos(Member member);

    /**
     * 특정 할 일을 수정합니다.
     *
     * @param id         수정할 할 일의 id입니다.
     * @param requestDto 수정할 정보입니다.
     * @param member 요청한 멤버입니다.
     * @return 수정이 완료된 할 일을 반환합니다.
     */
    TodoResponseDto updateTodo(int id, TodoRequestDto requestDto, Member member);

    /**
     * 특정 할 일을 삭제합니다.
     *
     * @param id     삭제할 할 일의 id입니다.
     * @param member 요청한 멤버입니다.
     * @return 삭제 완료 메시지를 반환합니다.
     */
    ApiResponseDto deleteTodo(int id, Member member);
}
