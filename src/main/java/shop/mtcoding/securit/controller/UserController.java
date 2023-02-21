package shop.mtcoding.securit.controller;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import shop.mtcoding.securit.model.UserRepository;
import shop.mtcoding.securit.util.PasswordEncoder;
import shop.mtcoding.securit.util.SHA256PasswordEncoder;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/main")
    public String main() {
        return "user/main";
    }

    @GetMapping("/loginForm")
    public String loginForm(User user) {
        PasswordEncoder passwordEncoder = new SHA256PasswordEncoder();

        String rawPassword = userRepository.findByPassword(user.getPassword());

        // encode메서드는 입력받은 비밀번호를 먼저 Bcrypt로 암호화하고, 그 결과를 SHA-256 해시 함수로 다시 암호화하여 반환합니다.
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // matches메서드는 입력된 비밀번호와 저장된 비밀번호가 일치하는지 확인하는 역할을 합니다.
        boolean isMatched = passwordEncoder.matches(rawPassword, encodedPassword);

        return "user/loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }
}
