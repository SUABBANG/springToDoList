package hello_spring.springToDoList.repository;

import hello_spring.springToDoList.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBynickname(String nickname); // nickname으로 사용자 조회
    Optional<User> findByEmail(String email); // email로 사용자 조회
    boolean existsByName(String nickname); // 닉네임 중복 체크
    boolean existByEmail(String email); // 이메일 중복 체크
}
