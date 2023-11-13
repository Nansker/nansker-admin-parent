package cn.nansker.service.auth.controller;

import cn.nansker.common.utils.result.ResultData;
import cn.nansker.common.utils.security.JwtUtils;
import cn.nansker.model.auth.SysLoginLog;
import cn.nansker.service.auth.service.SysLoginLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Nansker
 * @date 2023/11/11 23:48
 * @description TODO
 */
@Api(tags = "登录日志管理")
@RestController
@RequestMapping("/admin/system/log/login")
public class LogLoginController {
	@Autowired
	SysLoginLogService loginLogService;

	@ApiOperation("获取日志分页数据")
	@ApiParam(name = "loginLog", value = "查询条件参数")
	@GetMapping("/page")
	public ResultData listPage(SysLoginLog loginLog) {
		Page page = loginLogService.listPage(loginLog);
		return ResultData.ok().pageData(page);
	}

	@ApiOperation("获取用户登录日志")
	@GetMapping("/log")
	public ResultData getUserLoginLog(HttpServletRequest request) {
		Map userMap = JwtUtils.getUserIdAndUsernameMap(request);
		String username = (String) userMap.get("username");
		List<SysLoginLog> loginLogList = loginLogService.getLoginLogByUsername(username);
		return ResultData.ok().data(loginLogList);
	}

	@ApiOperation("根据id删除日志信息")
	@ApiParam(name = "id",value = "日志id")
	@DeleteMapping("/remove/{id}")
	public ResultData removeLogById(@PathVariable Long id) {
		loginLogService.removeById(id);
		return ResultData.ok();
	}

	@ApiOperation("批量删除日志信息")
	@ApiParam(name = "ids",value = "日志id集合")
	@DeleteMapping("/remove/batch")
	public ResultData removeLogByIds(@RequestBody List<Long> ids) {
		loginLogService.removeBatchByIds(ids);
		return ResultData.ok();
	}
}
