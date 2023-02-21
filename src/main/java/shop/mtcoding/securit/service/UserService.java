package shop.mtcoding.securit.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.securit.dto.UserReq.JoinReqDto;
import shop.mtcoding.securit.dto.UserReq.LoginReqDto;
import shop.mtcoding.securit.handler.ex.CustomException;
import shop.mtcoding.securit.model.User;
import shop.mtcoding.securit.model.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpSession session;

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
    };

    @Transactional(readOnly = true)
    public User 로그인(LoginReqDto loginReqDto) {
        User principal = userRepository.findByUsernameAndPassword(loginReqDto);
        if (principal == null) {
            throw new CustomException("유저네임 혹은 패스워드가 잘못 입력되었습니다");
        }
        return principal;
    }
}
