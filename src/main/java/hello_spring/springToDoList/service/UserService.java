package hello_spring.springToDoList.service;

import hello_spring.springToDoList.domain.User;
import hello_spring.springToDoList.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        String encodedPassword = passwordEncoder.encode(password); // 비밀번호 암호화
        User newUser = new User(nickname, encodedPassword, email);
        userRepository.save(newUser);
    }

    // 로그인
    public boolean loginCheck(String nickname, String password) {
        System.out.println("login check");
        // 데이터베이스에서 사용자 조회
        Optional<User> userOptional = userRepository.findBynickname(nickname);

        if (userOptional.isPresent()) {
            // 사용자 존재 시, 비밀번호 비교
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                // 비밀번호가 일치하면 true 반환
                return true;
            } else {
                // 비밀번호가 틀리면 false 반환
                System.out.println("비밀번호가 일치하지 않습니다.");
            }
        } else {
            // 사용자 없음
            System.out.println("사용자가 존재하지 않습니다.");
        }
        return false; // 로그인 실패
    }

    @Transactional
    public void deleteUser(String nickname) {
        User user = userRepository.findBynickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        userRepository.delete(user);
    }
}
