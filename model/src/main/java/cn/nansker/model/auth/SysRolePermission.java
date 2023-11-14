package cn.nansker.model.auth;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色菜单
 * </p>
 *
 * @author Nnasker
 * @since 2023-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_role_permission")
@ApiModel(value = "SysRolePermission对象", description = "角色菜单")
public class SysRolePermission implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键id")
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	@ApiModelProperty(value = "角色id")
	private Long roleId;

	@ApiModelProperty(value = "权限id")
	private Long permissionId;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "更新时间")
	private LocalDateTime updateTime;

	@ApiModelProperty(value = "删除标记（0:可用 1:已删除）")
	private Integer isDeleted;


}
