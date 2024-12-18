package hello_spring.springToDoList.repository;

import hello_spring.springToDoList.domain.TodoItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoItemRepository extends JpaRepository<TodoItems,Long> {
    List<TodoItems> findByListId(Long listId);

}
