package cn.nansker.service.auth.service;

import cn.nansker.model.auth.SysRoleMenu;
import cn.nansker.model.vo.RolePermissionAssignVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Nansker
* @description 针对表【sys_role_menu(角色菜单)】的数据库操作Service
* @createDate 2023-10-24 22:13:05
*/
@SuppressWarnings("ALL")
public interface SysRoleMenuService extends IService<SysRoleMenu> {

	/**
	 * 分配角色菜单权限
	 *
	 * @param userMenuAssign 角色菜单分配实体
	 * @return void
	 * @date 2023/11/6 18:48
	 */
	void doAssign(RolePermissionAssignVo userMenuAssign);

}
