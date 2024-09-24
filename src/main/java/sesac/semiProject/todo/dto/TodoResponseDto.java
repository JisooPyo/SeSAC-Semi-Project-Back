package sesac.semiProject.todo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponseDto {
    private Integer id;
    private String content;
    private Boolean completed;
    private LocalDate dueDate;
}
