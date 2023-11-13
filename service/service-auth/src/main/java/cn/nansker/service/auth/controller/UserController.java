package cn.nansker.service.auth.controller;

import cn.nansker.common.utils.result.ResultData;
import cn.nansker.common.utils.security.JwtUtils;
import cn.nansker.common.utils.security.PasswordUtils;
import cn.nansker.model.auth.SysUser;
import cn.nansker.model.vo.AccountVo;
import cn.nansker.model.vo.RouterVo;
import cn.nansker.model.vo.UserInfoVo;
import cn.nansker.model.vo.UserRoleAssignVo;
import cn.nansker.service.auth.service.SysUserRoleService;
import cn.nansker.service.auth.service.SysUserService;
import cn.nansker.service.base.exception.CustomException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
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
 * @date 2023/10/24 22:14
 * @description TODO
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin/system/user")
public class UserController {
	@Autowired
	SysUserService userService;
	@Autowired
	SysUserRoleService userRoleService;

	@ApiOperation("获取用户分页数据")
	@ApiParam(name = "user", value = "查询条件参数")
	@GetMapping("/page")
	public ResultData listPage(SysUser user) {
		Page page = userService.getUserPage(user);
		return ResultData.ok().pageData(page);
	}

	@ApiOperation("获取用户信息")
	@GetMapping("/info")
	public ResultData getUserInfoByToken(HttpServletRequest request) {
		Map userMap = JwtUtils.getUserIdAndUsernameMap(request);
		if (userMap == null) {
			throw new CustomException("无法获取用户信息");
		}
		UserInfoVo userInfo = userService.getUserInfoById(Long.valueOf((String) userMap.get("id")));
		return ResultData.ok().data(userInfo);
	}

	@ApiOperation("退出登录")
	@PostMapping("/logout")
	public ResultData logout() {
		return ResultData.ok();
	}

	@ApiOperation("根据id获取用户信息")
	@ApiParam(name = "id", value = "用户id")
	@GetMapping("/{id}")
	public ResultData getUserById(@PathVariable Long id) {
		UserInfoVo user = userService.getUserInfoById(id);
		return ResultData.ok().data(user);
	}

	@ApiOperation("根据id获取用户权限信息")
	@ApiParam(name = "id", value = "用户id")
	@GetMapping("/permission")
	public ResultData getPermissionRouterById(HttpServletRequest request) {
		Map userMap = JwtUtils.getUserIdAndUsernameMap(request);
		if (userMap == null) {
			throw new CustomException("无法获取用户信息");
		}
		List<RouterVo> permissions = userService.getPermissionRouterById(Long.valueOf((String) userMap.get("id")));
		return ResultData.ok().data(permissions);
	}

	@ApiOperation("添加用户信息")
	@ApiParam(name = "user", value = "用户信息")
	@PostMapping("/save")
	public ResultData save(@RequestBody SysUser user) {
		//TODO 账号唯一
		//设置用户默认密码123456
		if (StringUtils.isEmpty(user.getPassword())) {
			user.setPassword(PasswordUtils.hashPassword("123456"));
		}
		userService.save(user);
		return ResultData.ok();
	}

	@ApiOperation("密码重置")
	@ApiParam(name = "user", value = "用户信息")
	@PutMapping("/password")
	public ResultData updatePasswordByUsername(@RequestBody AccountVo account) {
		userService.updatePasswordByUsername(account);
		return ResultData.ok();
	}

	@ApiOperation("修改用户信息")
	@ApiParam(name = "user", value = "用户信息")
	@PutMapping("/update")
	public ResultData updateById(@RequestBody SysUser user) {
		userService.updateById(user);
		return ResultData.ok();
	}

	@ApiOperation("根据id删除用户信息")
	@ApiParam(name = "id", value = "用户id")
	@DeleteMapping("/remove/{id}")
	public ResultData removeUserById(@PathVariable Long id) {
		userService.removeById(id);
		return ResultData.ok();
	}

	@ApiOperation("批量删除用户信息")
	@ApiParam(name = "ids", value = "角色id集合")
	@DeleteMapping("/remove/batch")
	public ResultData removeUserById(@RequestBody List<Long> ids) {

		return ResultData.ok();
	}

	@ApiOperation("根据id获取已分配角色信息")
	@ApiParam(name = "id", value = "角色id")
	@GetMapping("/role/{id}")
	public ResultData getRoleByUserId(@PathVariable Long id) {
		List<Long> roles = userRoleService.getRoleByUserId(id);
		return ResultData.ok().data(roles);
	}

	@ApiOperation("分配用户角色")
	@ApiParam(name = "userRoleAssign", value = "角色权限信息")
	@PostMapping("/role/assign")
	public ResultData doUserRoleAssign(@RequestBody UserRoleAssignVo userRoleAssign) {
		userRoleService.doAssign(userRoleAssign);
		return ResultData.ok();
	}

}
