package cn.nansker.common.utils.result;

import lombok.Getter;

/**
 * @author: Nansker
 * @date: 2023/10/24 23:50
 * @description: 响应信息枚举类
 */
@Getter
public enum StatusCode {
	/**
	 * 请求类
	 * */
	SUCCESS(200,"操作成功"),
	ERROR(201, "操作失败"),
	SERVICE_ERROR(202, "服务异常"),
	DATA_ERROR(203, "数据异常"),
	ILLEGAL_REQUEST(204, "非法请求"),
	REPEAT_SUBMIT(205, "重复提交"),
	ARGUMENT_VALID_ERROR(206, "参数校验异常"),
	/**
	 * 校验类
	 * */
	LOGIN_AUTH(300, "未登陆"),
	LOGIN_TIME_OUT(301,"登录过期"),
	PERMISSION_NO(302, "访问未授权"),
	PERMISSION_ERROR(303, "认证失败"),
	ACCOUNT_ERROR(304, "账号不正确"),
	PASSWORD_ERROR(305, "密码不正确"),
	ACCOUNT_STOP( 307, "账号已停用"),
	/**
	 * 操作类
	 */
	NODE_ERROR( 401, "该节点下有子节点，不可以删除");

	private Integer code;
	private String message;

	private StatusCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
