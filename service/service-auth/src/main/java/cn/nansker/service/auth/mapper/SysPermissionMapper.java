package cn.nansker.service.auth.mapper;

import cn.nansker.model.auth.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Nansker
* @description 针对表【sys_permission(菜单表)】的数据库操作Mapper
* @createDate 2023-10-24 22:13:05
* @Entity cn.nansker.model.auth.SysPermission
*/
@SuppressWarnings("ALL")
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
	/**
	 * @param userId 用户Id
	 * @return java.util.List<cn.nansker.model.auth.SysPermission>
	 * @author Nansker
	 * @date 2023/11/7 17:20
	 * @description 根据用户id查询权限
	*/
	List<SysPermission> findPermissionByUserId(Long userId);
}




