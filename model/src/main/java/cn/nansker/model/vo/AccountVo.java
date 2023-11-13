package cn.nansker.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Nansker
 * @date 2023/11/7 9:15
 * @description TODO
 */
@Data
@ApiModel(value = "UserLoginVo对象", description = "用户登录")
public class AccountVo implements Serializable {
	@ApiModelProperty(value = "登录账号")
	private String username;
	@ApiModelProperty(value = "登录密码")
	private String password;
}
