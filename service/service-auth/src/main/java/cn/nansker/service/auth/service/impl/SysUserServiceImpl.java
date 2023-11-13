package cn.nansker.service.auth.service.impl;

import cn.nansker.common.utils.result.StatusCode;
import cn.nansker.common.utils.security.JwtUtils;
import cn.nansker.common.utils.security.PasswordUtils;
import cn.nansker.model.auth.SysUser;
import cn.nansker.model.vo.RouterVo;
import cn.nansker.model.vo.UserInfoVo;
import cn.nansker.model.vo.AccountVo;
import cn.nansker.service.auth.mapper.SysUserMapper;
import cn.nansker.service.auth.service.SysMenuService;
import cn.nansker.service.auth.service.SysUserRoleService;
import cn.nansker.service.auth.service.SysUserService;
import cn.nansker.service.base.exception.CustomException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nansker
 * @description 针对表【sys_user(用户表)】的数据库操作Service实现
 * @createDate 2023-10-24 22:13:05
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
	@Autowired
	SysUserRoleService userRoleService;
	@Autowired
	SysMenuService menuService;

	@Override
	public String login(AccountVo user) {

		String username = user.getUsername();
		String password = user.getPassword();

		//用户登录信息非空判断,检测异常
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			throw new CustomException("登录信息为空");
		}

		//获取登录用户信息
		SysUser dbUser = getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));

		//用户未注册
		if (dbUser == null) {
			throw new CustomException(StatusCode.ACCOUNT_ERROR.getMessage());
		}

		//校验密码
		//TODO 使用了默认加密盐值
		if (!PasswordUtils.verifyPassword(password, dbUser.getPassword())) {
			throw new CustomException(StatusCode.PASSWORD_ERROR.getMessage());
		}

		//校验用户状态
		if (dbUser.getStatus() == 0) {
			//已禁用
			throw new CustomException(StatusCode.ACCOUNT_STOP.getMessage());
		}

		//使用JWT生成token
		String token = JwtUtils.getJwtToken(String.valueOf(dbUser.getId()), dbUser.getUsername());
		return token;
	}

	@Override
	public UserInfoVo getUserInfoById(Long userId) {
		QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
		userQueryWrapper.eq("id", userId);
		SysUser user = getOne(userQueryWrapper);
		List<Long> roles = userRoleService.getRoleByUserId(userId);

		UserInfoVo userInfo = new UserInfoVo();
		BeanUtils.copyProperties(user, userInfo);
		userInfo.setRoles(roles);
		return userInfo;
	}

	@Override
	public Page getUserPage(SysUser user) {
		Page<SysUser> pageParam = new Page<>(user.getPageNum(), user.getPageSize());
		LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.like(StringUtils.isNotEmpty(user.getUsername()), SysUser::getUsername, user.getUsername());
		queryWrapper.like(StringUtils.isNotEmpty(user.getName()), SysUser::getName, user.getName());
		queryWrapper.like(user.getStatus() != null, SysUser::getStatus, user.getStatus());
		queryWrapper.orderByAsc(SysUser::getId);
		Page<SysUser> page = page(pageParam, queryWrapper);
		return page;
	}

	@Override
	public List<RouterVo> getPermissionRouterById(Long id) {
		List<RouterVo> permissions = menuService.getPermissionRouterByUserId(id);
		return permissions;
	}

	@Override
	public SysUser getUserInfoByUsername(String username) {
		QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", username);
		return getOne(queryWrapper);
	}

	@Override
	public void updatePasswordByUsername(AccountVo user) {
		UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("username", user.getUsername());
		updateWrapper.set("password",PasswordUtils.hashPassword(user.getPassword()));
		update(updateWrapper);
	}

}