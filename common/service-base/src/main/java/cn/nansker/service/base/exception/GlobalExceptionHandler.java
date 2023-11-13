package cn.nansker.service.base.exception;

import cn.nansker.common.utils.result.ResultData;
import cn.nansker.common.utils.result.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Nansker
 * @date 2023/8/8 5:11
 * @description 统一异常处理类
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResultData error(Exception e) {
		log.error(e.getMessage());
		return ResultData.error().data(StatusCode.SERVICE_ERROR);
	}

	@ExceptionHandler(CustomException.class)
	public ResultData customError(CustomException e) {
		log.error(e.getMessage());
		return ResultData.error().message(e.getMsg());
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResultData securityError(AccessDeniedException e) {
		log.error(e.getMessage());
		return ResultData.error().data(StatusCode.PERMISSION_ERROR);
	}
}