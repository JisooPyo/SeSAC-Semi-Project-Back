package sesac.semiProject.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sesac.semiProject.todo.model.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
    List<Todo> findAllByMember_IdOrderByIdAsc(int id);
}
