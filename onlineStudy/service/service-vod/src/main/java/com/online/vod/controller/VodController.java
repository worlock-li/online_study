package com.online.vod.controller;

import com.online.commonutils.Result;
import com.online.vod.handler.VodAddressHandler;
import com.online.vod.service.VodService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/vod/video")
@CrossOrigin
public class VodController {
	@Autowired
	VodService vodService;

	@PostMapping("/vodUpload")
	@ApiOperation("上传视频到阿里云")
	public Result vodUpload(MultipartFile file) {
		String vodId = vodService.vodUpload(file);
		return Result.ok().data("id", vodId);
	}
}
