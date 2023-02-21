package shop.mtcoding.securit.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256PasswordEncoder {

    public static String encode(String rawPassword) {
        try {
            // SHA-256 해시코드 생성
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 입력받은 비밀번호를 바이트 배열로 변환
            byte[] passwordBytes = rawPassword.getBytes();

            // 해시코드에 바이트 배열 추가
            digest.update(passwordBytes);

            // 해시코드 생성 및 16진수 문자열로 변환
            byte[] hashedBytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}