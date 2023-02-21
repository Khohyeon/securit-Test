package shop.mtcoding.securit.dto;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.securit.model.User;

public class UserReq {

    @Setter
    @Getter
    public static class JoinReqDto {
        private String username;
        private String password;
        private String email;

        public User toModel() {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            return user;
        }
    }

    @Setter
    @Getter
    public static class LoginReqDto {
        private String username;
        private String password;
    }
}
