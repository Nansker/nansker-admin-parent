package cn.nansker.security.service;

import cn.nansker.model.auth.SysLoginLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author: Nansker
 * @date: 2023/11/11 22:58
 * @description: 用户日志操作
 */
public interface LoginLogService extends IService<SysLoginLog> {

	/**
	 * 记录用户登录日志
	 *
	 * @param loginLog 日志信息实体
	 * @return void
	 * @date 2023/11/11 22:59
	 */
	void recordLoginLog(SysLoginLog loginLog);
}
