package cn.nansker.service.auth.service;

import cn.nansker.model.auth.SysUserRole;
import cn.nansker.model.vo.UserRoleAssignVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Nansker
 * @description 针对表【sys_user_role(用户角色)】的数据库操作Service
 * @createDate 2023-10-24 22:13:05
 */
@SuppressWarnings("ALL")
public interface SysUserRoleService extends IService<SysUserRole> {
	/**
	 * 根据用户id查询角色信息
	 *
	 * @param id 用户id
	 * @return List<Long>
	 * @date 2023/10/29 16:53
	 */
	List<Long> getRoleByUserId(Long id);

	/**
	 * @param userRoleAssign 用户角色分配对象
	 * @return void
	 * @author Nansker
	 * @date 2023/10/29 17:18
	 * @description 分配用户角色
	 */
	void doAssign(UserRoleAssignVo userRoleAssign);

}
