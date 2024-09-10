package sesac.semiProject.todo.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sesac.semiProject.todo.dto.TodoResponseDto;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String content;     // 내용

    @Column(nullable = false)
    private Boolean completed;  // 완료 여부
    private LocalDate dueDate;  // 마감일

    public TodoResponseDto toDto() {
        return TodoResponseDto.builder()
            .id(id)
            .content(content)
            .completed(completed)
            .dueDate(dueDate)
            .build();
    }
}
