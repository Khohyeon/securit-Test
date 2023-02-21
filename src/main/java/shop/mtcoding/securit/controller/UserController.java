package shop.mtcoding.securit.controller;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.securit.dto.UserReq.JoinReqDto;
import shop.mtcoding.securit.handler.ex.CustomException;
import shop.mtcoding.securit.model.UserRepository;
import shop.mtcoding.securit.service.UserService;
import shop.mtcoding.securit.util.PasswordEncoder;
import shop.mtcoding.securit.util.SHA256PasswordEncoder;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

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

    // @PostMapping("/login")
    // public String login(LoginReqDto loginReqDto, Model model) {
    // if (loginReqDto.getUsername() == null || loginReqDto.getUsername().isEmpty())
    // {
    // throw new CustomException("username을 작성해주세요", HttpStatus.BAD_REQUEST);
    // }
    // if (loginReqDto.getPassword() == null || loginReqDto.getPassword().isEmpty())
    // {
    // throw new CustomException("password를 작성해주세요", HttpStatus.BAD_REQUEST);
    // }
    // User principal = userService.로그인(loginReqDto);
    // session.setAttribute("principal", principal);
    // return "account/main";
    // }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @PostMapping("/join")
    public String join(JoinReqDto joinReqDto) { // Dto로 받는 것이 좋다
        // 유효성 검사
        if (joinReqDto.getUsername() == null || joinReqDto.getUsername().isEmpty()) {
            throw new CustomException("username을 작성해주세요", HttpStatus.BAD_REQUEST);
        }
        if (joinReqDto.getPassword() == null || joinReqDto.getPassword().isEmpty()) {
            throw new CustomException("password를 작성해주세요", HttpStatus.BAD_REQUEST);
        }
        if (joinReqDto.getEmail() == null || joinReqDto.getEmail().isEmpty()) {
            throw new CustomException("fullname을 작성해주세요", HttpStatus.BAD_REQUEST);
        }
        userService.회원가입(joinReqDto);

        return "redirect:/loginForm";
    }

}
