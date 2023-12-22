package com.sparta.nbcamptodo;

import com.sparta.nbcamptodo.config.JpaConfig;
import com.sparta.nbcamptodo.config.QuerydslConfig;
import com.sparta.nbcamptodo.config.S3Config;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import({JpaConfig.class, S3Config.class, QuerydslConfig.class})
class NbcampTodoApplicationTests {

	@Test
	void contextLoads() {
	}

}
