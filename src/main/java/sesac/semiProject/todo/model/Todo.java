package sesac.semiProject.todo.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sesac.semiProject.member.model.Member;
import sesac.semiProject.todo.dto.TodoRequestDto;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public TodoResponseDto toDto() {
        return TodoResponseDto.builder()
            .id(id)
            .content(content)
            .completed(completed)
            .dueDate(dueDate)
            .build();
    }

    public void update(TodoRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.completed = requestDto.isCompleted();
        this.dueDate = requestDto.getDueDate();
    }
}
