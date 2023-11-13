package cn.nansker.security.custom;

import cn.nansker.common.utils.security.PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Nansker
 * @date 2023/11/9 22:27
 * @description 自定义密码组件
 */
@Slf4j
@Component
public class CustomPasswordEncoder implements PasswordEncoder {
	@Override
	public String encode(CharSequence charSequence) {
		return PasswordUtils.hashPassword(String.valueOf(charSequence));
	}

	@Override
	public boolean matches(CharSequence charSequence, String s) {
		return PasswordUtils.verifyPassword(String.valueOf(charSequence),s);
	}

}
