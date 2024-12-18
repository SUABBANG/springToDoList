package hello_spring.springToDoList.service;

import hello_spring.springToDoList.domain.TodoItems;
import hello_spring.springToDoList.domain.TodoLists;
import hello_spring.springToDoList.domain.User;
import hello_spring.springToDoList.repository.TodoItemRepository;
import hello_spring.springToDoList.repository.TodoListRepository;
import hello_spring.springToDoList.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoService {
    @Autowired
    private TodoListRepository todoListRepository;
    @Autowired
    private TodoItemRepository todoItemRepository;
    @Autowired
    private UserRepository userRepository;

    public List<TodoLists> getAllTodoLists(){
        List<TodoLists> todoLists = todoListRepository.findAll();
        if (todoLists.isEmpty()){
            System.out.println("No todolist");
        }
        return todoLists;
    }
    public void addTodoList(TodoLists todoLists){
        todoListRepository.save(todoLists);
    }
    public List<TodoItems> getTodoItemsByListId(Long listId){
        List<TodoItems> todoItems = todoItemRepository.findByListId(listId);
        System.out.println(todoItems);
        if (todoItems == null) {
            System.out.println("No todoItems");
        }
        return todoItems;
    }
    public void addTodoItem(TodoItems todoItems) {
        todoItemRepository.save(todoItems);
    }

    public void updateTodoItem(TodoItems todoItems) {
        todoItemRepository.save(todoItems);
    }

    public void deleteTodoItem(Long itemId) {
        todoItemRepository.deleteById(itemId);
    }
    public Long getUserIdByNickname(String nickname) {
        return userRepository.findBynickname(nickname)
                .map(user -> user.getUserId())
                .orElse(null); // 사용자 없으면 null 반환
    }
    // user_id로 TodoLists 조회
    public List<TodoLists> getTodoListsByUserId(Long userId) {
        return todoListRepository.findByUserId(userId);
    }
    // listId로 해당 TodoList 조회
    public TodoLists getTodoListById(Long listId) {
        return todoListRepository.findById(listId).orElse(null);
    }

    // userId가 listId를 소유하는지 확인
    public boolean isListOwnedByUser(Long listId, Long userId) {
        return todoListRepository.existsByListIdAndUserId(listId, userId);
    }
    public TodoItems getTodoItemById(Long itemId) {
        return todoItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Todo item with ID " + itemId + " not found"));
    }
}
