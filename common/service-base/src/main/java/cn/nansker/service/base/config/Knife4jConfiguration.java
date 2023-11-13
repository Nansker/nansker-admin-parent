package cn.nansker.service.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author Nansker
 * @date 2023/10/24 23:25
 * @description TODO
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {
	@Bean(value = "dockerBean")
	public Docket dockerBean() {
		//指定使用Swagger2规范
		Docket docket=new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(new ApiInfoBuilder()
						//描述字段支持Markdown语法
						.title("通用管理系统-API文档")
						.description("# 本文描述了后台管理系统微服务接口定义")
						.contact(new Contact("南山客","www.nansker.cn","nansker@163.com"))
						.version("1.0")
						.build())
				//分组名称
				.groupName("Api")
				.select()
				//这里指定Controller扫描包路径
				.apis(RequestHandlerSelectors.basePackage("cn.nansker"))
				.paths(PathSelectors.any())
				.build();
		return docket;
	}
}
