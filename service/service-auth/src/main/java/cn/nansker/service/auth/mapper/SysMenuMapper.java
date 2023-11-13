package cn.nansker.service.auth.mapper;

import cn.nansker.model.auth.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Nansker
* @description 针对表【sys_menu(菜单表)】的数据库操作Mapper
* @createDate 2023-10-24 22:13:05
* @Entity cn.nansker.model.auth.SysMenu
*/
@SuppressWarnings("ALL")
public interface SysMenuMapper extends BaseMapper<SysMenu> {
	/**
	 * @param userId 用户Id
	 * @return java.util.List<cn.nansker.model.auth.SysMenu>
	 * @author Nansker
	 * @date 2023/11/7 17:20
	 * @description 根据用户id查询权限
	*/
	List<SysMenu> findPermissionByUserId(Long userId);
}




