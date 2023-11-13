package cn.nansker.service.auth.service.impl;

import cn.nansker.service.auth.mapper.SysUserRoleMapper;
import cn.nansker.service.auth.service.SysUserRoleService;
import cn.nansker.model.auth.SysUserRole;
import cn.nansker.model.vo.UserRoleAssignVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nansker
 * @description 针对表【sys_user_role(用户角色)】的数据库操作Service实现
 * @createDate 2023-10-24 22:13:05
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

	@Override
	public List<Long> getRoleByUserId(Long id) {
		//查询用户已分配角色
		LambdaQueryWrapper<SysUserRole> userRoleQueryWrapper = new LambdaQueryWrapper<>();
		userRoleQueryWrapper.eq(SysUserRole::getUserId, id);
		List<SysUserRole> userRoleList = list(userRoleQueryWrapper);
		List<Long> userRolesIds = new ArrayList<>();
		//获取所有角色id
		userRoleList.stream().forEach(r -> {
			userRolesIds.add(r.getRoleId());
		});
		return userRolesIds;
	}

	@Override
	public void doAssign(UserRoleAssignVo userRoleAssign) {
		//删除已分配的角色
		LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SysUserRole::getUserId, userRoleAssign.getUserId());
		remove(queryWrapper);

		//添加用户角色信息
		ArrayList<SysUserRole> userRoleList = new ArrayList<>();
		userRoleAssign.getRoleIdList().stream().forEach(roleId->{
			SysUserRole userRole = new SysUserRole();
			userRole.setUserId(userRoleAssign.getUserId());
			userRole.setRoleId(roleId);
			userRoleList.add(userRole);
		});

		saveBatch(userRoleList);
	}

}




