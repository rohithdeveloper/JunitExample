package com.example.junit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = DemoApplicationTests.class)
class DemoApplicationTests {
    @Test
    void contextLoads() {
    }
}