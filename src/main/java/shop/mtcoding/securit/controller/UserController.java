package shop.mtcoding.securit.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.securit.dto.UserReq.JoinReqDto;
import shop.mtcoding.securit.dto.UserReq.LoginReqDto;
import shop.mtcoding.securit.handler.ex.CustomException;
import shop.mtcoding.securit.model.User;
import shop.mtcoding.securit.model.UserRepository;
import shop.mtcoding.securit.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @GetMapping("/main")
    public String main() {
        return "user/main";
    }

    @GetMapping("/loginForm")
    public String loginForm(User user) {

        return "user/loginForm";
    }

    @PostMapping("/login")
    public String login(LoginReqDto loginReqDto, Model model, String rawPassword) {

        if (loginReqDto.getUsername() == null || loginReqDto.getUsername().isEmpty()) {
            throw new CustomException("username을 작성해주세요", HttpStatus.BAD_REQUEST);
        }
        if (loginReqDto.getPassword() == null || loginReqDto.getPassword().isEmpty()) {
            throw new CustomException("password를 작성해주세요", HttpStatus.BAD_REQUEST);
        }

        userService.로그인(loginReqDto);

        // session.setAttribute("principal", principal);
        return "redirect:/main";
    }

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
