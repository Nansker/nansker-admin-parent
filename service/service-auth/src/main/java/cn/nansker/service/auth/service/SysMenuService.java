package cn.nansker.service.auth.service;

import cn.nansker.model.auth.SysMenu;
import cn.nansker.model.vo.RouterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Nansker
* @description 针对表【sys_menu(菜单表)】的数据库操作Service
* @createDate 2023-10-24 22:13:05
*/
@SuppressWarnings("ALL")
public interface SysMenuService extends IService<SysMenu> {
	/**
	 * 获取全部菜单信息
	 *
	 * @param menu 查询条件参数
	 * @return List<SysMenu>
	 * @date 2023/11/5 20:51
	 */
	List<SysMenu> getPermissionList(SysMenu menu);

	/**
	 * 根据id删除菜单
	 *
	 * @param id 菜单id
	 * @return void
	 * @date 2023/11/5 21:17
	 */
	void removeMenuById(Long id);

	/**
	 * 根据用户id获取权限路由
	 *
	 * @param userId 用户id
	 * @return List<RouterVo>
	 * @date 2023/11/7 16:48
	 */
	List<RouterVo> getPermissionRouterByUserId(Long userId);

	/**
	 * 根据用户id获取权限
	 *
	 * @param userId 用户id
	 * @return List<SysMenu>
	 * @date 2023/11/7 17:03
	 */
	List<SysMenu> getPermissionByUserId(Long userId);

	/**
	 * 根据用户名获取操作权限
	 *
	 * @param username 用户名
	 * @return List<String>
	 * @date 2023/11/13 21:24
	 */
	List<String> getPermissionHandleByUsername(String username);
}
