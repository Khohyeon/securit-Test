package shop.mtcoding.securit.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SHA256PasswordEncoder implements PasswordEncoder {

    private PasswordEncoder bcryptEncoder;

    @Override
    public String encode(CharSequence rawPassword) {
        String bcryptEncodedPassword = bcryptEncoder.encode(rawPassword);
        return hashWithSHA256(bcryptEncodedPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String hashOfRawPassword = hashWithSHA256(rawPassword.toString());
        return bcryptEncoder.matches(hashOfRawPassword, encodedPassword);
    }

    private String hashWithSHA256(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(str.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }
}