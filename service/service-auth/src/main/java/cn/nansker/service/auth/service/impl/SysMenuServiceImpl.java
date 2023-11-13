package cn.nansker.service.auth.service.impl;

import cn.nansker.service.auth.mapper.SysMenuMapper;
import cn.nansker.service.auth.service.SysMenuService;
import cn.nansker.service.auth.service.SysRoleMenuService;
import cn.nansker.service.auth.service.SysUserRoleService;
import cn.nansker.service.auth.service.SysUserService;
import cn.nansker.service.auth.util.MenuHelper;
import cn.nansker.service.auth.util.RouterHelper;
import cn.nansker.model.auth.SysMenu;
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
 * @description 针对表【sys_menu(菜单表)】的数据库操作Service实现
 * @createDate 2023-10-24 22:13:05
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
	@Autowired
	SysUserService userService;
	@Autowired
	SysUserRoleService userRoleService;
	@Autowired
	SysRoleMenuService roleMenuService;

	@Override
	public List<SysMenu> getPermissionList(SysMenu menu) {
		LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();

		if (menu != null) {
			queryWrapper.like(StringUtils.isNotEmpty(menu.getName()), SysMenu::getName, menu.getName());
			queryWrapper.eq(menu.getStatus() != null, SysMenu::getStatus, menu.getStatus());
		}

		queryWrapper.orderByAsc(SysMenu::getSortValue);

		List<SysMenu> list = list(queryWrapper);
		List<SysMenu> result = MenuHelper.buildTree(list);
		return result;
	}

	@Override
	public void removeMenuById(Long id) {
		//判断当前菜单下是否有子菜单
		QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_id", id);
		Long count = baseMapper.selectCount(queryWrapper);
		if (count > 0) {
			throw new CustomException(StatusCode.NODE_ERROR.getMessage());
		}
		removeById(id);
	}

	@Override
	public List<RouterVo> getPermissionRouterByUserId(Long userId) {
		List<SysMenu> permissionList = getPermissionByUserId(userId);
		//根据菜单信息构建权限路由
		List<RouterVo> routerList = RouterHelper.buildRouters(permissionList);
		return routerList;
	}

	@Override
	public List<SysMenu> getPermissionByUserId(Long userId) {
		//获取用户角色信息
		List<Long> userRoles = userRoleService.getRoleByUserId(userId);
		List<SysMenu> menuList;
		boolean isAdmin = false;
		//查询角色权限
		for (Long roleId : userRoles) {
			//判断超级管理员
			if (roleId == 100001) {
				isAdmin = true;
			}
		}
		if (isAdmin) {
			menuList = baseMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1).orderByAsc("sort_value"));
		} else {
			menuList = baseMapper.findPermissionByUserId(userId);
		}

		//构建树形
		List<SysMenu> result = MenuHelper.buildTree(menuList);

		return result;
	}

	@Override
	public List<String> getPermissionHandleByUsername(String username) {
		//获取用户角色信息
		SysUser user = userService.getUserInfoByUsername(username);
		List<Long> userRoles = userRoleService.getRoleByUserId(user.getId());
		List<SysMenu> menuList;
		boolean isAdmin = false;
		//查询角色权限
		for (Long roleId : userRoles) {
			//判断超级管理员
			if (roleId == 100001) {
				isAdmin = true;
			}
		}
		if (isAdmin) {
			menuList = baseMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1).orderByAsc("sort_value"));
		} else {
			menuList = baseMapper.findPermissionByUserId(user.getId());
		}
		List<String> permsList = new ArrayList<>();
		for (SysMenu menu : menuList) {
			if (!StringUtils.isEmpty(menu.getPerms())){
				permsList.add(menu.getPerms());
			}
		}
		return permsList;
	}

}




