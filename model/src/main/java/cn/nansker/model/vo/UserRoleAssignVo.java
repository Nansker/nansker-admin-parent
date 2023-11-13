package cn.nansker.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Nansker
 * @date 2023/10/29 17:14
 * @description 用户角色分配数据
 */
@Data
@ApiModel(value = "UserRoleAssignVo对象", description = "用户角色分配")
public class UserRoleAssignVo {
	@ApiModelProperty(value = "用户id")
	private Long userId;
	@ApiModelProperty(value = "角色id列表")
	private List<Long> roleIdList;

}
