package cn.nansker.service.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * @author Nansker
 * @date 2023/9/21 17:24
 * @description RestTemplate配置类
 */
@Configuration
public class RestTemplateConfig {
	@Bean
	public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
		RestTemplate restTemplate = new RestTemplate(factory);
		// 支持中文编码
		restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return restTemplate;
	}

	@Bean
	public ClientHttpRequestFactory factory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		//单位为ms
		factory.setReadTimeout(5000);
		//单位为ms
		factory.setConnectTimeout(5000);
		return factory;
	}

}
