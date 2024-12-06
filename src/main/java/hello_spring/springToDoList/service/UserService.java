package hello_spring.springToDoList.service;

import hello_spring.springToDoList.domain.User;
import hello_spring.springToDoList.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this. passwordEncoder = passwordEncoder;
    }

    // 닉네임 중복 체크
    public boolean checkNicknameExists(String nickname){
        return userRepository.existsByNickname(nickname);
    }
    // 이메일 중복 체크
    public boolean checkEmailExists(String email){
        return userRepository.existsByEmail(email);
    }
    // 회원 가입
    public void registerUser(String nickname, String password, String email){
        System.out.println("password : "+password);
        String encodedPassword = passwordEncoder.encode(password); // 비밀번호 암호화
        System.out.println("_________service__________");
        System.out.println("ID : "+nickname);
        System.out.println("encodedPassword : "+encodedPassword);
        System.out.println("email : "+email);
        User newUser = new User(nickname, encodedPassword, email);
        userRepository.save(newUser);
    }
}
