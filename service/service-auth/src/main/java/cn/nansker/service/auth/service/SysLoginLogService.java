package cn.nansker.service.auth.service;

import cn.nansker.model.auth.SysLoginLog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Nansker
 * @description 针对表【sys_login_log(系统访问记录)】的数据库操作Service
 * @createDate 2023-10-24 22:13:05
 */
@SuppressWarnings("ALL")
public interface SysLoginLogService extends IService<SysLoginLog> {
	/**
	 * 根据用户名获取登录日志
	 *
	 * @param username 用户名
	 * @return List<SysLoginLog>
	 * @date 2023/11/11 23:32
	 */
	List<SysLoginLog> getLoginLogByUsername(String username);

	/**
	 * 获取登录日志分页数据
	 *
	 * @param loginLog 查询条件参数
	 * @return Page
	 * @date 2023/11/11 23:32
	 */
	Page listPage(SysLoginLog loginLog);

}
