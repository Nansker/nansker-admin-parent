package cn.nansker.service.auth.service;

import cn.nansker.model.auth.SysUser;
import cn.nansker.model.vo.RouterVo;
import cn.nansker.model.vo.UserInfoVo;
import cn.nansker.model.vo.AccountVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Nansker
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2023-10-24 22:13:05
*/
@SuppressWarnings("ALL")
public interface SysUserService extends IService<SysUser> {
	/**
	 * 管理员用户登录
	 *
	 * @param user 登录信息实体
	 * @return String
	 * @date 2023/11/7 9:17
	 */
	String login(AccountVo user);

	/**
	 * @param userId 用户id
	 * @return cn.nansker.model.vo.UserInfoVo
	 * @author Nansker
	 * @date 2023/11/7 9:43
	 * @description 获取用户信息
	*/
	UserInfoVo getUserInfoById(Long userId);

	/**
	 * 获取用户分页信息
	 *
	 * @param user 查询条件参数
	 * @return Page
	 * @date 2023/10/25 19:16
	 */
	Page getUserPage(SysUser user);

	/**
	 * 根据用户id获取权限路由
	 *
	 * @param id 用户id
	 * @return List<RouterVo>
	 * @date 2023/11/7 20:48
	 */
	List<RouterVo> getPermissionRouterById(Long id);

	/**
	 * 根据用户名获取用户信息
	 *
	 * @param username 用户名
	 * @return SysUser
	 * @date 2023/11/9 22:40
	 */
	SysUser getUserInfoByUsername(String username);

	/**
	 * 根据用户名更新密码
	 *
	 * @param account 账号密码信息实体
	 * @return void
	 * @date 2023/11/11 15:36
	 */
	void updatePasswordByUsername(AccountVo account);

}
