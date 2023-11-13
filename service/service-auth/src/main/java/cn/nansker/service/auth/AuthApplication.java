package cn.nansker.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Nansker
 * @date 2023/10/24 22:05
 * @description TODO
 */
@SpringBootApplication
@MapperScan("cn.nansker.service.auth.mapper")
@Slf4j
public class AuthApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
		log.info("Api文档地址：http://localhost:10001/doc.html");
	}
}
