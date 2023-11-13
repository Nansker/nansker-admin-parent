package cn.nansker.service.base.exception;

import lombok.Data;

/**
 * @author Nansker
 * @date 2023/9/18 21:54
 * @description 自定义异常类
 */
@Data
public class CustomException extends RuntimeException {
	private String msg;

	public CustomException(String msg) {
		super(msg);
		this.msg = msg;
	}

}
