package com.zimug.courses.security.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author AricSun
 * @date 2021.12.28 16:28
 */
@Slf4j
public class PasswordEncoderTest {

    @Test
    void bCryptPasswordTest(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "123456";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        log.info("原始密码：" + rawPassword);
        log.info("加密后密码：" + encodedPassword);

        log.info(rawPassword + "是否匹配" + encodedPassword + ": "
                + passwordEncoder.matches(rawPassword, encodedPassword));

        log.info("654321是否匹配" + encodedPassword + ": "
                + passwordEncoder.matches("654321", encodedPassword));
    }
}
