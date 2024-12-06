package hello_spring.springToDoList.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String encodedPassword = encoder.encode("admin");
//        System.out.println("인코딩 pw");
//        System.out.println(encodedPassword);  // 콘솔에 출력된 값을 복사합니다.
        return new BCryptPasswordEncoder();
    }
    
    @Bean // 첫 사용자 로그인 기능 없애줌
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http 
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
                )
        ;
        return http.build();
    }
}




