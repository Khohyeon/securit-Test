package shop.mtcoding.securit.util;

import org.springframework.util.DigestUtils;

public class HexSha256PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        // 입력받은 비밀번호를 바이트 배열로 변환
        byte[] passwordBytes = rawPassword.toString().getBytes();

        // SHA-256으로 암호화한 결과를 16진수 문자열로 변환하여 반환
        return DigestUtils.md5DigestAsHex(passwordBytes);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // 저장된 암호화된 비밀번호와 입력받은 비밀번호가 같은지 확인
        return encode(rawPassword).equals(encodedPassword);
    }

}