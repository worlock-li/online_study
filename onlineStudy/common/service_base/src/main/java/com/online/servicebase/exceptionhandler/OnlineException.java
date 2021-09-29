package com.online.servicebase.exceptionhandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * 自定义异样类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineException extends RuntimeException {

	private Integer code;
	private String message;
}
