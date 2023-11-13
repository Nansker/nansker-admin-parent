package cn.nansker.service.auth.service.impl;

import cn.nansker.model.auth.SysLoginLog;
import cn.nansker.security.service.LoginLogService;
import cn.nansker.service.auth.mapper.SysLoginLogMapper;
import cn.nansker.service.auth.service.SysLoginLogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nansker
 * @description 针对表【sys_login_log(系统访问记录)】的数据库操作Service实现
 * @createDate 2023-10-24 22:13:05
 */
@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements LoginLogService, SysLoginLogService {

	@Override
	public void recordLoginLog(SysLoginLog loginLog) {
		save(loginLog);
	}

	@Override
	public List<SysLoginLog> getLoginLogByUsername(String username) {
		QueryWrapper<SysLoginLog> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", username);
		queryWrapper.orderByAsc("access_time");
		return list(queryWrapper);
	}

	@Override
	public Page listPage(SysLoginLog loginLog) {
		Page<SysLoginLog> pageParam = new Page<>(loginLog.getPageNum(), loginLog.getPageSize());
		LambdaQueryWrapper<SysLoginLog> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.like(StringUtils.isNotEmpty(loginLog.getUsername()), SysLoginLog::getUsername, loginLog.getUsername());
		queryWrapper.orderByAsc(SysLoginLog::getAccessTime);
		Page<SysLoginLog> page = page(pageParam, queryWrapper);
		return page;
	}

}




