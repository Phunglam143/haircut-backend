package com.haircut;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import com.haircut.config.TestSecurityConfig;

/**
 * Unit test for simple App.
 */
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {HaircutApplication.class, TestSecurityConfig.class})
class AppTest {

    @Test
    void contextLoads() {
        // Test will pass if Spring context loads successfully
    }

}
