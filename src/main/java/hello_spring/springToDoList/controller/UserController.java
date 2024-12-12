package hello_spring.springToDoList.controller;

import hello_spring.springToDoList.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    @PostMapping("/login")
    public String loginUser(
            @RequestParam("nickname") String nickname,
            @RequestParam("password") String password,
            HttpSession session,
            Model model
    ){
        if (userService.loginCheck(nickname, password)){
            session.setAttribute("nickname",nickname); // 세션 사용자 정보 저장
            System.out.println("세션에 저장된 닉네임: " + session.getAttribute("nickname"));
            System.out.println("로그인 성공");
            return "home";
        }else{
            model.addAttribute("error","Invalid nickname or password");
            System.out.println("로그인 실패");
            return "login";
        }
    }
    @Controller
    public class LogoutController {

        @GetMapping("/logout")
        public String logout(HttpSession session) {
            // 세션 무효화
            session.invalidate();
            System.out.println("로그아웃 완료");
            // 로그아웃 후 로그인 페이지로 이동
            return "redirect:/login";
        }
    }

    @GetMapping("/register")
    public String showRegisterationForm(){
        return "register";  // 회원 가입 페이지
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam(value = "nickname") String nickname, @RequestParam(value = "password") String password, @RequestParam(value = "email") String email, Model model) {
        System.out.println("POST 요청 도달");
        if (userService.checkNicknameExists(nickname)) {
            model.addAttribute("error", "Nickname already exists");
            return "register";  // 닉네임 중복시 다시 등록 페이지로
        }
        if (userService.checkEmailExists(email)) {
            model.addAttribute("error", "Email already exists");
            return "register";  // 이메일 중복시 다시 등록 페이지로
        }

        userService.registerUser(nickname, password, email);
        return "redirect:/";  // 성공 시 리다이렉트
    }
    @PostMapping("/delete-user")
    public String deleteUser(HttpSession session){
        String nickname = (String) session.getAttribute("nickname");
        System.out.println(nickname);
        if(nickname!=null){
            userService.deleteUser(nickname);
            session.invalidate(); //세션 무효화
            System.out.println("탈퇴 완료");
        }
        return "redirect:/login?delete=true";
    }
}
