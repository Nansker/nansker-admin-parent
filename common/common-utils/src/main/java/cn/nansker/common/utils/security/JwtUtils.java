package cn.nansker.common.utils.security;

import cn.nansker.common.utils.result.StatusCode;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nansker
 * @date 2023/9/17 0:53
 * @description JWT工具类
 */
@Slf4j
public class JwtUtils {
	//token有效期 1天
	public static final long EXPIRE = 60 * 60 * 24 * 1000;
	public static final String APP_SECRET = "nansker2023";

	/**
	 * 生成token
	 *
	 * @param id       用户id
	 * @param username 用户名
	 * @return String
	 * @date 2023/9/18 0:43
	 */
	public static String getJwtToken(String id, String username) {
		String jwtToken = Jwts.builder().setHeaderParam("typ", "JWT").setHeaderParam("alg", "HS256").setSubject("user")
				//设置过期时间
				.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
				//设置包含
				.claim("id", id).claim("username", username).signWith(SignatureAlgorithm.HS256, APP_SECRET).compact();
		return jwtToken;
	}

	/**
	 * 验证token有效性
	 *
	 * @param token
	 * @return boolean
	 * @date 2023/11/13 23:47
	 */
	public static boolean checkToken(String token) {
		if (StringUtils.isEmpty(token)) {
			return false;
		}
		try {
			Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * @param request
	 * @return
	 * @description 判断token是否存在与有效
	 */
	public static boolean checkToken(HttpServletRequest request) {
		try {
			String jwtToken = request.getHeader("token");
			if (StringUtils.isEmpty(jwtToken)) {
				return false;
			}
			Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 获取token包含信息的Map集合
	 *
	 * @param request
	 * @return Map
	 * @date 2023/11/13 23:48
	 */
	public static Map getUserIdAndUsernameMap(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String jwtToken = request.getHeader("token");
		if (StringUtils.isEmpty(jwtToken)) {
			return null;
		}
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken).getBody();
		} catch (ExpiredJwtException e) {
			log.error(e.getMessage());
			if (isTokenExpired(claims)) {
				throw new RuntimeException(StatusCode.LOGIN_TIME_OUT.getMessage());
			}
		}
		map.put("id", (String) claims.get("id"));
		map.put("username", (String) claims.get("username"));
		return map;
	}

	/**
	 * 判断token是否过期
	 *
	 * @param claims
	 * @return Boolean
	 * @date 2023/11/11 15:00
	 */
	public static Boolean isTokenExpired(Claims claims) {
		Date expiration = claims.getExpiration();
		//和当前时间进行对比来判断是否过期
		return new Date(System.currentTimeMillis()).after(expiration);
	}

}
