package cn.nansker.security.custom;

import cn.nansker.model.auth.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author Nansker
 * @date 2023/11/9 22:31
 * @description TODO
 */
public class CustomUser extends User {
	private SysUser sysUser;

	public CustomUser(SysUser sysUser, Collection<? extends GrantedAuthority> authorities) {
		super(sysUser.getUsername(), sysUser.getPassword(), authorities);
		this.sysUser = sysUser;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

}
