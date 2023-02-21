package shop.mtcoding.securit.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.securit.dto.UserReq.JoinReqDto;
import shop.mtcoding.securit.dto.UserReq.LoginReqDto;
import shop.mtcoding.securit.handler.ex.CustomException;
import shop.mtcoding.securit.model.UserRepository;
import shop.mtcoding.securit.util.HexSha256PasswordEncoder;
import shop.mtcoding.securit.util.PasswordEncoder;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpSession session;

    PasswordEncoder passwordEncoder = new HexSha256PasswordEncoder();

    @Transactional
    public void 회원가입(JoinReqDto joinReqDto) {
        // User sameUser = userRepository.findByUsername(joinReqDto.getUsername());
        // if (sameUser != null) {
        // throw new CustomException("동일한 username이 존재합니다");
        // }

        int result = userRepository.insert(joinReqDto.toModel());
        if (result != 1) {
            throw new CustomException("회원가입실패");
        }

        // System.out.println("테스트 : " + rawPassword);
        // encode메서드는 입력받은 비밀번호를 먼저 Bcrypt로 암호화하고, 그 결과를 SHA-256 해시 함수로 다시 암호화하여 반환합니다.
    };

    @Transactional(readOnly = true)
    public String 로그인(LoginReqDto loginReqDto) {

        // if (rawPassword == null) {
        // throw new CustomException("유저네임 혹은 패스워드가 잘못 입력되었습니다");
        // }
        userRepository.findByUsernameAndPassword(loginReqDto);

        String rawPassword = loginReqDto.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        System.out.println("테스트 : " + encodedPassword);
        // matches메서드는 입력된 비밀번호와 저장된 비밀번호가 일치하는지 확인하는 역할을 합니다.
        boolean isMatched = passwordEncoder.matches(rawPassword, encodedPassword);
        if (isMatched == false) {
            throw new CustomException("비밀번호가 올바르지 않습니다.");
        }
        return "rawPassword";
    }
}