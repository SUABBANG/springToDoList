package hello_spring.springToDoList.controller;

import hello_spring.springToDoList.domain.TodoItems;
import hello_spring.springToDoList.domain.TodoLists;
import hello_spring.springToDoList.service.ToDoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/todolist")
public class TodoController {

    @Autowired
    private ToDoService todoService;

    @GetMapping
    public String getTodoLists(HttpSession session, Model model) {
        // 세션에서 닉네임 가져오기
        String nickname = (String) session.getAttribute("nickname");
        System.out.println(nickname);
        if (nickname == null) {
            // 로그인이 안 된 경우 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }

        // 닉네임으로 user_id 조회
        Long userId = todoService.getUserIdByNickname(nickname);
        if (userId == null) {
            model.addAttribute("error", "Invalid user.");
            return "error"; // 사용자 정보가 없으면 에러 페이지
        }

        // 해당 사용자의 TodoLists 조회
        System.out.println("userid : " + userId);
        List<TodoLists> todoLists = todoService.getTodoListsByUserId(userId);
        model.addAttribute("todoLists", todoLists);

        return "todolists"; // 해당 사용자에 맞는 할 일 목록 반환
    }

    @PostMapping("/add")
    public String addTodoList(@ModelAttribute TodoLists todoLists, HttpSession session, Model model) {
        // 세션에서 닉네임 가져오기
        String nickname = (String) session.getAttribute("nickname");
        if (nickname == null) {
            // 로그인하지 않은 경우 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }

        // 닉네임으로 user_id 조회
        Long userId = todoService.getUserIdByNickname(nickname);
        if (userId == null) {
            model.addAttribute("error", "Invalid user.");
            return "error"; // 사용자 정보가 없으면 에러 페이지
        }

        // user_id를 TodoLists 객체에 설정
        todoLists.setUserId(userId);

        // 할 일 목록 추가
        todoService.addTodoList(todoLists);

        return "redirect:/todolist"; // 완료 후 리스트 페이지로 리다이렉트
    }

    @GetMapping("/list/{listId}")
    public String getTodoItems(@PathVariable("listId") Long listId, HttpSession session, Model model) {
        // 세션에서 닉네임 가져오기
        String nickname = (String) session.getAttribute("nickname");
        if (nickname == null) {
            // 로그인하지 않은 경우 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }

        // 닉네임으로 user_id 조회
        Long userId = todoService.getUserIdByNickname(nickname);
        if (userId == null) {
            model.addAttribute("error", "Invalid user.");
            return "error";
        }

        // 리스트가 현재 사용자 소유인지 확인
        if (!todoService.isListOwnedByUser(listId, userId)) {
            model.addAttribute("error", "Access denied.");
            return "error"; // 접근 권한이 없으면 에러 페이지
        }

        // 리스트 내 아이템 조회
        List<TodoItems> todoItems = todoService.getTodoItemsByListId(listId);
        model.addAttribute("todoItems", todoItems);

        // 리스트 제목 정보 추가
        TodoLists todoList = todoService.getTodoListById(listId);
        model.addAttribute("todoList", todoList);

        return "todoitems"; // 아이템 목록을 보여주는 뷰 반환
    }
    // ITEM
    @PostMapping("/item/add/{listId}")
    public String addTodoItem(@PathVariable("listId") Long listId, @ModelAttribute TodoItems todoItems) {
        // 아이템의 리스트 ID 설정
        todoItems.setListId(listId);

        // 서비스 호출하여 아이템 추가
        todoService.addTodoItem(todoItems);

        // 해당 리스트로 리다이렉트
        return "redirect:/todolist/list/" + listId;
    }
    @PostMapping("/item/update/{itemId}")
    public String updateTodoItem(
            @PathVariable("itemId") Long itemId,
            @RequestParam("itemTitle") String itemTitle,
            @RequestParam("itemDescription") String itemDescription,
            @RequestParam("statusCode") int statusCode
    ) {
        TodoItems todoItem = todoService.getTodoItemById(itemId);
        todoItem.setItemTitle(itemTitle);
        todoItem.setItemDescription(itemDescription);
        todoItem.setStatusCode(statusCode);
        todoService.updateTodoItem(todoItem);

        return "redirect:/todolist/list/" + todoItem.getListId();
    }

    @PostMapping("/item/delete/{itemId}")
    public String deleteTodoItem(@PathVariable("itemId") Long itemId) {
        TodoItems todoItem = todoService.getTodoItemById(itemId);
        Long listId = todoItem.getListId();
        todoService.deleteTodoItem(itemId);

        return "redirect:/todolist/list/" + listId;
    }
}

