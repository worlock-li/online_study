package com.online.edu.controller;

import com.online.commonutils.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/edu/user")
@CrossOrigin
// @CrossOrigin 解决跨域问题
public class LoginController {

	/**
	 * login
	 */
	@PostMapping("/login")
	public Result login() {
		return Result.ok().data("token", "admin");
	}

	/**
	 * info
	 */
	@GetMapping("/info")
	public Result info() {
		return Result.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://img0.baidu.com/it/u=1725973846,1988797627&fm=26&fmt=auto");
	}

}
