package sesac.semiProject.todo.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TodoResponseDto {
    private Integer id;
    private String content;
    private Boolean completed;
    private LocalDate dueDate;
}
