package cn.nansker.model.vo;

import cn.nansker.model.auth.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Nansker
 * @date 2023/11/7 9:40
 * @description TODO
 */
@Data
@ApiModel(value = "UserInfoVo对象", description = "用户信息")
public class UserInfoVo extends SysUser implements Serializable {
	@ApiModelProperty(value = "用户角色信息")
	private List<Long> roles;
}
