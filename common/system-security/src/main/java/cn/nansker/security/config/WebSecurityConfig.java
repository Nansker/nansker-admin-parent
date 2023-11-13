package cn.nansker.security.config;

import cn.nansker.security.custom.CustomPasswordEncoder;
import cn.nansker.security.filter.TokenAuthenticationFilter;
import cn.nansker.security.filter.TokenLoginFilter;
import cn.nansker.security.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Nansker
 * @date 2023/11/9 22:11
 * @description 访问配置类
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private CustomPasswordEncoder customPasswordEncoder;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private LoginLogService loginLogService;

	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//防护设置 关闭csrf
		http.csrf().disable()
				//开启跨域
				//.cors().and()
				.authorizeRequests().anyRequest().authenticated().and()
				//设置除登录外，其他接口使用token认证
				.addFilterBefore(new TokenAuthenticationFilter(redisTemplate), UsernamePasswordAuthenticationFilter.class)
				//添加过滤器
				.addFilter(new TokenLoginFilter(authenticationManager(), redisTemplate, loginLogService)).addFilter(new TokenAuthenticationFilter(redisTemplate));
		//禁用session
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//指定加密器
		auth.userDetailsService(userDetailsService).passwordEncoder(customPasswordEncoder);
	}

	/**
	 * @param web
	 * @return void
	 * @author Nansker
	 * @date 2023/11/9 23:58
	 * @description 配置忽略过滤请求
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/favicon.ico", "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/doc.html");
	}

}
