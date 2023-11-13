package cn.nansker.service.auth.service;

import cn.nansker.model.auth.SysRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Nansker
* @description 针对表【sys_role(角色)】的数据库操作Service
* @createDate 2023-10-24 22:13:05
*/
@SuppressWarnings("ALL")
public interface SysRoleService extends IService<SysRole> {
	/**
	 * 获取角色分页信息
	 *
	 * @param role 查询条件参数
	 * @return Page
	 * @date 2023/10/25 14:38
	 */
	Page listPage(SysRole role);

}
