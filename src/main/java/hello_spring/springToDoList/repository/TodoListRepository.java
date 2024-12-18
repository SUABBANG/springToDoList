package hello_spring.springToDoList.repository;

import hello_spring.springToDoList.domain.TodoLists;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoListRepository extends JpaRepository<TodoLists, Long> {
    List<TodoLists> findByUserId(Long userId);
    boolean existsByListIdAndUserId(Long listId, Long userId);

}
