package cn.nansker.service.auth.service.impl;

import cn.nansker.service.auth.mapper.SysPermissionMapper;
import cn.nansker.service.auth.service.SysPermissionService;
import cn.nansker.service.auth.service.SysRolePermissionService;
import cn.nansker.service.auth.service.SysUserRoleService;
import cn.nansker.service.auth.service.SysUserService;
import cn.nansker.service.auth.util.MenuHelper;
import cn.nansker.service.auth.util.RouterHelper;
import cn.nansker.model.auth.SysPermission;
import cn.nansker.model.auth.SysUser;
import cn.nansker.model.vo.RouterVo;
import cn.nansker.service.base.exception.CustomException;
import cn.nansker.common.utils.result.StatusCode;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nansker
 * @description 针对表【sys_permission(菜单表)】的数据库操作Service实现
 * @createDate 2023-10-24 22:13:05
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {
	@Autowired
	SysUserService userService;
	@Autowired
	SysUserRoleService userRoleService;
	@Autowired
	SysRolePermissionService rolePermissionService;

	@Override
	public List<SysPermission> getPermissionList(SysPermission permission) {
		LambdaQueryWrapper<SysPermission> queryWrapper = new LambdaQueryWrapper<>();

		if (permission != null) {
			queryWrapper.like(StringUtils.isNotEmpty(permission.getName()), SysPermission::getName, permission.getName());
			queryWrapper.eq(permission.getStatus() != null, SysPermission::getStatus, permission.getStatus());
		}

		queryWrapper.orderByAsc(SysPermission::getSortValue);

		List<SysPermission> list = list(queryWrapper);
		List<SysPermission> result = MenuHelper.buildTree(list);
		return result;
	}

	@Override
	public void removePermissionById(Long id) {
		//判断当前菜单下是否有子菜单
		QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_id", id);
		Long count = baseMapper.selectCount(queryWrapper);
		if (count > 0) {
			throw new CustomException(StatusCode.NODE_ERROR.getMessage());
		}
		removeById(id);
	}

	@Override
	public List<RouterVo> getPermissionRouterByUserId(Long userId) {
		List<SysPermission> permissionList = getPermissionByUserId(userId);
		//根据菜单信息构建权限路由
		List<RouterVo> routerList = RouterHelper.buildRouters(permissionList);
		return routerList;
	}

	@Override
	public List<SysPermission> getPermissionByUserId(Long userId) {
		//获取用户角色信息
		List<Long> userRoles = userRoleService.getRoleByUserId(userId);
		List<SysPermission> permissionList;
		boolean isAdmin = false;
		//查询角色权限
		for (Long roleId : userRoles) {
			//判断超级管理员
			if (roleId == 100001) {
				isAdmin = true;
			}
		}
		if (isAdmin) {
			permissionList = baseMapper.selectList(new QueryWrapper<SysPermission>().eq("status", 1).orderByAsc("sort_value"));
		} else {
			permissionList = baseMapper.findPermissionByUserId(userId);
		}

		//构建树形
		List<SysPermission> result = MenuHelper.buildTree(permissionList);

		return result;
	}

	@Override
	public List<String> getPermissionHandleByUsername(String username) {
		//获取用户角色信息
		SysUser user = userService.getUserInfoByUsername(username);
		List<Long> userRoles = userRoleService.getRoleByUserId(user.getId());
		List<SysPermission> permissionList;
		boolean isAdmin = false;
		//查询角色权限
		for (Long roleId : userRoles) {
			//判断超级管理员
			if (roleId == 100001) {
				isAdmin = true;
			}
		}
		if (isAdmin) {
			permissionList = baseMapper.selectList(new QueryWrapper<SysPermission>().eq("status", 1).orderByAsc("sort_value"));
		} else {
			permissionList = baseMapper.findPermissionByUserId(user.getId());
		}
		List<String> permsList = new ArrayList<>();
		for (SysPermission permission : permissionList) {
			if (!StringUtils.isEmpty(permission.getPerms())){
				permsList.add(permission.getPerms());
			}
		}
		return permsList;
	}

}




