package dev.cydome.awesomepizza;

import dev.cydome.awesomepizza.config.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(useMainMethod = SpringBootTest.UseMainMethod.ALWAYS)
class AwesomepizzaApplicationTests {

	@Test
	void contextLoads() {
	}

}
