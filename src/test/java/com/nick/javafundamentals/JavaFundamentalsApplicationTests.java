package com.nick.javafundamentals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Smoke test: the Spring context loads. This also confirms CGLIB/Byte Buddy proxy
 * creation works on the local JDK, which is the thing most likely to break when
 * running a newer runtime than the framework was built against.
 */
@SpringBootTest
@ActiveProfiles("test")
class JavaFundamentalsApplicationTests {

    @Test
    void contextLoads() {
    }
}
