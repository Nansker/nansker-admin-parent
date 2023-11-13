package cn.nansker.service.auth.service.impl;

import cn.nansker.service.auth.mapper.SysRoleMenuMapper;
import cn.nansker.service.auth.service.SysRoleMenuService;
import cn.nansker.model.auth.SysRoleMenu;
import cn.nansker.model.vo.RolePermissionAssignVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nansker
 * @description 针对表【sys_role_menu(角色菜单)】的数据库操作Service实现
 * @createDate 2023-10-24 22:13:05
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

	@Override
	public void doAssign(RolePermissionAssignVo roleMenuAssign) {
		//删除已分配的菜单
		LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SysRoleMenu::getRoleId, roleMenuAssign.getRoleId());
		remove(queryWrapper);

		//添加角色权限信息
		List<SysRoleMenu> roleMenuList = new ArrayList<>();

		for (Long menuId : roleMenuAssign.getMenuIdList()) {
			SysRoleMenu roleMenu = new SysRoleMenu();
			roleMenu.setRoleId(roleMenuAssign.getRoleId());
			roleMenu.setMenuId(menuId);
			roleMenuList.add(roleMenu);
		}

		saveBatch(roleMenuList);
	}

}




