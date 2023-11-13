package cn.nansker.service.auth.service.impl;

import cn.nansker.service.auth.service.SysMenuService;
import cn.nansker.service.auth.service.SysUserService;
import cn.nansker.model.auth.SysUser;
import cn.nansker.security.custom.CustomUser;
import cn.nansker.service.base.exception.CustomException;
import cn.nansker.common.utils.result.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nansker
 * @date 2023/11/9 22:34
 * @description TODO
 */
@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private SysUserService userService;

	@Autowired
	private SysMenuService menuService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUser sysUser = userService.getUserInfoByUsername(username);
		if (sysUser == null) {
			throw new UsernameNotFoundException(StatusCode.ACCOUNT_ERROR.getMessage());
		}

		if (sysUser.getStatus().intValue() == 0) {
			throw new CustomException(StatusCode.ACCOUNT_STOP.getMessage());
		}
		//查询用户权限
		List<String> permissions = menuService.getPermissionHandleByUsername(username);
		//设置权限
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for (String permission : permissions) {
			authorities.add(new SimpleGrantedAuthority(permission.trim()));
		}
		return new CustomUser(sysUser, authorities);
	}

}
