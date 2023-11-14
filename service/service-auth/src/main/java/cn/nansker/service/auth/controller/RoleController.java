package cn.nansker.service.auth.controller;

import cn.nansker.service.auth.service.SysRolePermissionService;
import cn.nansker.service.auth.service.SysRoleService;
import cn.nansker.model.auth.SysRole;
import cn.nansker.model.auth.SysRolePermission;
import cn.nansker.model.vo.RolePermissionAssignVo;
import cn.nansker.common.utils.result.ResultData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Nansker
 * @date 2023/10/24 22:14
 * @description TODO
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/admin/system/role")
public class RoleController {
	@Autowired
	private SysRoleService roleService;
	@Autowired
	SysRolePermissionService rolePermissionService;

	@ApiOperation("获取全部角色列表")
	@GetMapping("/all")
	public ResultData getRoleList() {
		List<SysRole> list = roleService.list();
		return ResultData.ok().data(list);
	}

	@ApiOperation("获取角色分页数据")
	@ApiParam(name = "role", value = "查询条件参数")
	@GetMapping("/page")
	public ResultData listPage(SysRole role) {
		Page page = roleService.listPage(role);
		return ResultData.ok().pageData(page);
	}

	@ApiOperation("根据id获取角色信息")
	@ApiParam(name = "id", value = "角色id")
	@GetMapping("/{id}")
	public ResultData getRoleByUserId(@PathVariable Long id) {
		SysRole result = roleService.getById(id);
		return ResultData.ok().data(result);
	}

	@ApiOperation("添加角色信息")
	@ApiParam(name = "role", value = "角色信息")
	@PostMapping("/save")
	public ResultData save(@RequestBody SysRole role){
		roleService.save(role);
		return ResultData.ok();
	}

	@ApiOperation("修改角色信息")
	@ApiParam(name = "role", value = "角色信息")
	@PutMapping("/update")
	public ResultData updateById(@RequestBody SysRole role){
		roleService.updateById(role);
		return ResultData.ok();
	}

	@ApiOperation("根据id删除角色信息")
	@ApiParam(name = "id",value = "角色id")
	@DeleteMapping("/remove/{id}")
	public ResultData removeRoleById(@PathVariable Long id) {
		roleService.removeById(id);
		return ResultData.ok();
	}

	@ApiOperation("批量删除角色信息")
	@ApiParam(name = "ids",value = "角色id集合")
	@DeleteMapping("/remove/batch")
	public ResultData removeRoleByIds(@RequestBody List<Long> ids) {
		roleService.removeBatchByIds(ids);
		return ResultData.ok();
	}

	@ApiOperation("根据角色id获取菜单信息")
	@ApiParam(name = "id", value = "角色id")
	@GetMapping("/permission/{id}")
	public ResultData getRolePermissionByRoleId(@PathVariable Long id) {
		QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("role_id",id);
		List<SysRolePermission> result = rolePermissionService.list(queryWrapper);
		return ResultData.ok().data(result);
	}

	@ApiOperation("分配角色权限")
	@ApiParam(name = "rolePermissionAssign", value = "角色权限信息实体")
	@PostMapping("/permission/assign")
	public ResultData doRolePermissionAssign(@RequestBody RolePermissionAssignVo rolePermissionAssign) {
		rolePermissionService.doAssign(rolePermissionAssign);
		return ResultData.ok();
	}
}
