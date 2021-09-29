package com.online.servicebase.exceptionhandler;

import com.online.commonutils.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/***
 * 异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 全局异常， 设置 @ExceptionHandler 的值，改为特定异常
	 * @ExceptionHandler : 指定出现什么异常时执行方法
	 * 返回result类， 需要在common_base中依赖common_utils
 	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Result error(Exception e) {
		e.printStackTrace();
		return Result.error().message("执行统一异常处理器");
	}
}
