package hello_spring.springToDoList.controller;

import hello_spring.springToDoList.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(){
        System.out.println("GET 요청 도달");
        return "index";  // 홈 페이지
    }

    @GetMapping("/login")
    public String showLoginForm(){
        return "login";  // 로그인 페이지로 리다이렉트
    }

    @GetMapping("/register")
    public String showRegisterationForm(){
        return "register";  // 회원 가입 페이지
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String nickname, @RequestParam String password, @RequestParam String email, Model model) {
        System.out.println("POST 요청 도달");
        if (userService.checkNicknameExists(nickname)) {
            model.addAttribute("error", "Nickname already exists");
            return "register";  // 닉네임 중복시 다시 등록 페이지로
        }
        if (userService.checkEmailExists(email)) {
            model.addAttribute("error", "Email already exists");
            return "register";  // 이메일 중복시 다시 등록 페이지로
        }

        System.out.println("_________controlloer__________");
        System.out.println("ID : "+nickname);
        System.out.println("password : "+password);
        System.out.println("email : "+email);

        userService.registerUser(nickname, password, email);
        return "redirect:/index";  // 성공 시 리다이렉트
    }
}
