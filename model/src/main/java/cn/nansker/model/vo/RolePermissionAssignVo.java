package cn.nansker.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Nansker
 * @date 2023/10/29 17:14
 * @description 角色权限分配数据
 */
@Data
@ApiModel(value = "RolePermissionAssignVo", description = "角色权限分配")
public class RolePermissionAssignVo {
	@ApiModelProperty(value = "用户id")
	private Long roleId;
	@ApiModelProperty(value = "菜单id列表")
	private List<Long> permissionIdList;

}
