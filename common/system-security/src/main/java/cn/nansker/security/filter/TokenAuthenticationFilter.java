package cn.nansker.security.filter;

import cn.nansker.common.utils.ResponseUtils;
import cn.nansker.common.utils.result.ResultData;
import cn.nansker.common.utils.result.StatusCode;
import cn.nansker.common.utils.security.JwtUtils;
import cn.nansker.service.base.RedisLevelName;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Nansker
 * @date 2023/11/9 23:28
 * @description 访问过滤器
 */
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {
	private RedisTemplate redisTemplate;
	private String loginPath = "/login";

	public TokenAuthenticationFilter(RedisTemplate redisTemplate) {
		this.redisTemplate  = redisTemplate;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

		//放行登录接口
		if (request.getRequestURI().contains(loginPath)) {
			chain.doFilter(request, response);
			return;
		}
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
		if (authentication != null) {
			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(request, response);
		} else {
			ResponseUtils.out(response, ResultData.ok().data(StatusCode.PERMISSION_NO));
		}
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader("token");
		if (!StringUtils.isEmpty(token)) {
			Map userMap = JwtUtils.getUserIdAndUsernameMap(request);
			if (userMap != null) {
				String authenticationsString = (String) redisTemplate.opsForValue().get(RedisLevelName.USER_PERMISSION + userMap.get("username"));
				List<Map> mapList = JSON.parseArray(authenticationsString, Map.class);
				List<SimpleGrantedAuthority> authorities = new ArrayList<>();
				for (Map map : mapList) {
					authorities.add(new SimpleGrantedAuthority((String) map.get("authority")));
				}
				return new UsernamePasswordAuthenticationToken(userMap.get("username"), null,authorities);
			}
		}
		return null;
	}

}
