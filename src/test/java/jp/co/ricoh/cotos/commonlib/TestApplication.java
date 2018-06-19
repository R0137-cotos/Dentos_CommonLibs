package jp.co.ricoh.cotos.commonlib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import jp.co.ricoh.cotos.commonlib.security.TestWebSecurityConfig;

@SpringBootApplication
@Import(TestWebSecurityConfig.class)
public class TestApplication {
	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class);
	}
}
