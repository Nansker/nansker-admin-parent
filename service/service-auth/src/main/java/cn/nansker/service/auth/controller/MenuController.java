package cn.nansker.service.auth.controller;

import cn.nansker.service.auth.service.SysMenuService;
import cn.nansker.model.auth.SysMenu;
import cn.nansker.common.utils.result.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Nansker
 * @date 2023/10/30 20:07
 * @description TODO
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/admin/system/permission")
public class MenuController {
	@Autowired
	SysMenuService menuService;

	@ApiOperation("获取全部菜单列表")
	@GetMapping("/all")
	public ResultData getPermissionList(SysMenu menu) {
		List<SysMenu> result = menuService.getPermissionList(menu);
		return ResultData.ok().data(result);
	}

	@ApiOperation("根据id获取菜单信息")
	@ApiParam(name = "id", value = "菜单id")
	@GetMapping("/{id}")
	public ResultData getPermissionByUserId(@PathVariable Long id) {
		SysMenu result = menuService.getById(id);
		return ResultData.ok().data(result);
	}

	@ApiOperation("添加菜单信息")
	@ApiParam(name = "menu", value = "实体信息")
	@PostMapping("/save")
	public ResultData save(@RequestBody SysMenu menu) {
		menuService.save(menu);
		return ResultData.ok();
	}

	@ApiOperation("修改菜单信息")
	@ApiParam(name = "menu", value = "实体信息")
	@PutMapping("/update")
	public ResultData updateById(@RequestBody SysMenu menu) {
		menuService.updateById(menu);
		return ResultData.ok();
	}

	@ApiOperation("根据id删除菜单信息")
	@ApiParam(name = "id", value = "菜单id")
	@DeleteMapping("/remove/{id}")
	public ResultData removeById(@PathVariable Long id) {
		menuService.removeMenuById(id);
		return ResultData.ok();
	}

}
