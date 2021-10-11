package com.online.oss.controller;

import com.online.commonutils.Result;
import com.online.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/edu/oss")
@CrossOrigin
public class OssController {

	@Autowired
	private OssService ossService;

	@PostMapping("/upload")
	public Result uploadOssFile(MultipartFile file) {
		// 上传头像
		String url = ossService.uploadOssAvatar(file);
		return Result.ok().data("url", url);
	}

}
