package cn.nansker.security.filter;

import cn.nansker.common.utils.ResponseUtils;
import cn.nansker.common.utils.result.ResultData;
import cn.nansker.common.utils.result.StatusCode;
import cn.nansker.common.utils.security.JwtUtils;
import cn.nansker.model.auth.SysLoginLog;
import cn.nansker.model.vo.AccountVo;
import cn.nansker.security.custom.CustomUser;
import cn.nansker.security.service.LoginLogService;
import cn.nansker.service.base.RedisLevelName;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nansker
 * @date 2023/11/9 22:48
 * @description 登录过滤器，对用户密码进行登录校验
 */
@Slf4j
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
	private LoginLogService loginLogService;
	private RedisTemplate redisTemplate;

	public TokenLoginFilter(AuthenticationManager authenticationManager, RedisTemplate redisTemplate, LoginLogService loginLogService) {
		this.setAuthenticationManager(authenticationManager);
		this.setPostOnly(false);
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/system/user/login", "POST"));
		this.redisTemplate = redisTemplate;
		this.loginLogService = loginLogService;
	}

	/**
	 * @param request
	 * @param response
	 * @return org.springframework.security.core.Authentication
	 * @author Nansker
	 * @date 2023/11/9 23:05
	 * @description 获取用户登录账号密码
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		try {
			AccountVo userLoginVo = new ObjectMapper().readValue(request.getInputStream(), AccountVo.class);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userLoginVo.getUsername(), userLoginVo.getPassword());
			return this.getAuthenticationManager().authenticate(authenticationToken);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 认证成功回调
	 *
	 * @param request
	 * @param response
	 * @param chain
	 * @param authResult
	 * @return void
	 * @date 2023/11/9 23:24
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		//取认证对象
		CustomUser customUser = (CustomUser) authResult.getPrincipal();
		//生成token
		String token = JwtUtils.getJwtToken(String.valueOf(customUser.getSysUser().getId()), customUser.getSysUser().getUsername());

		//保存用户权限数据到redis
		redisTemplate.opsForValue().set(RedisLevelName.USER_PERMISSION + customUser.getSysUser().getUsername(), JSON.toJSONString(customUser.getAuthorities()));
		Map<String, Object> map = new HashMap<>();
		map.put("token", token);

		//保存登录日志信息
		SysLoginLog loginLog = new SysLoginLog();
		loginLog.setUsername(customUser.getSysUser().getUsername());
		loginLog.setAccessTime(LocalDateTime.now());
		loginLog.setMsg("登陆成功");
		loginLog.setStatus(1);
		loginLog.setIpaddr(request.getRemoteAddr());
		loginLogService.recordLoginLog(loginLog);
		log.info("【" + loginLog.getUsername() + "】用户登录成功");
		//返回信息
		ResponseUtils.out(response, ResultData.ok().data(map));
	}

	/**
	 * 认证失败回调
	 *
	 * @param request
	 * @param response
	 * @param failed
	 * @return void
	 * @date 2023/11/9 23:25
	 */
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
		if (failed.getCause() instanceof RuntimeException) {
			ResponseUtils.out(response, ResultData.error().code(204).message(failed.getMessage()));
		}
		ResponseUtils.out(response, ResultData.error().data(StatusCode.PERMISSION_ERROR));
	}

}
