package sesac.semiProject.todo.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import sesac.semiProject.common.constants.ValidationConstants;
import sesac.semiProject.member.model.Member;
import sesac.semiProject.todo.model.Todo;

@Getter
@Setter
public class TodoRequestDto {
    @NotBlank(message = ValidationConstants.EMPTY_CONTENT)
    private String content;
    private LocalDate dueDate;
    private boolean completed;

    public Todo toEntity(Member member) {
        return Todo.builder()
            .content(content)
            .completed(completed)
            .dueDate(dueDate)
            .member(member)
            .build();
    }
}
