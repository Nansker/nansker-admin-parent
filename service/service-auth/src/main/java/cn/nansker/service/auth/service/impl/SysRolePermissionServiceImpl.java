package cn.nansker.service.auth.service.impl;

import cn.nansker.service.auth.mapper.SysRolePermissionMapper;
import cn.nansker.service.auth.service.SysRolePermissionService;
import cn.nansker.model.auth.SysRolePermission;
import cn.nansker.model.vo.RolePermissionAssignVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nansker
 * @description 针对表【sys_role_permission(角色菜单)】的数据库操作Service实现
 * @createDate 2023-10-24 22:13:05
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements SysRolePermissionService {

	@Override
	public void doAssign(RolePermissionAssignVo permissionAssign) {
		//删除已分配的菜单
		LambdaQueryWrapper<SysRolePermission> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SysRolePermission::getRoleId, permissionAssign.getRoleId());
		remove(queryWrapper);

		//添加角色权限信息
		List<SysRolePermission> rolePermissionList = new ArrayList<>();

		for (Long permissionId : permissionAssign.getPermissionIdList()) {
			SysRolePermission rolePermission = new SysRolePermission();
			rolePermission.setRoleId(permissionAssign.getRoleId());
			rolePermission.setPermissionId(permissionId);
			rolePermissionList.add(rolePermission);
		}

		saveBatch(rolePermissionList);
	}

}




