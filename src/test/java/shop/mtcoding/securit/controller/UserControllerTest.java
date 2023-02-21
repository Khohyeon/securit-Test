package shop.mtcoding.securit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.securit.util.HexSha256PasswordEncoder;
import shop.mtcoding.securit.util.PasswordEncoder;

/*
 * SpringBootTest는 통합테스트 (실제 환경과 동일하게 Bean이 생성됨)
 * AutoConfigureMockMvc는 Mock 환경의 Ioc 컨테이너에 Mockmvc Bean이 생성됨
 * 테스트는 순서가 뭐가먼저 실행될지 모른다.
 */

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void join_test() throws Exception {
        // given
        String requestBody = "username=ssar11&password=1234&email=ssar@nate.com";

        // when
        ResultActions resultActions = mvc.perform(post("/join").content(requestBody)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

        // then
        resultActions.andExpect(status().is3xxRedirection());
    }

    @Test
    public void login_test() throws Exception {
        PasswordEncoder passwordEncoder = new HexSha256PasswordEncoder();

        // given
        String requestBody = "username=ssar&password=1234";
        String rawPassword = "1234";

        // when
        ResultActions resultActions = mvc.perform(post("/login").content(requestBody)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

        System.out.println("테스트 : rawPassword = " + rawPassword);

        // encode메서드는 입력받은 비밀번호를 먼저 Bcrypt로 암호화하고, 그 결과를 SHA-256 해시 함수로 다시 암호화하여 반환합니다.
        String encodedPassword = passwordEncoder.encode(rawPassword);
        if (encodedPassword == null) {
            System.out.println("테스트 : encodedPassword가 null");
        }
        System.out.println("테스트 : encodedPassword = " + encodedPassword);

        // matches메서드는 입력된 비밀번호와 저장된 비밀번호가 일치하는지 확인하는 역할을 합니다.
        boolean isMatched = passwordEncoder.matches(rawPassword, encodedPassword);

        // then 검증
        assertThat(isMatched);
        resultActions.andExpect(status().is4xxClientError());
    }

}