package com.online.vod.controller;

import com.online.commonutils.Result;
import com.online.vod.handler.VodAddressHandler;
import com.online.vod.service.VodService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

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
		return Result.ok().data("vodId", vodId);
	}

	@DeleteMapping("/removeVod/{id}")
	@ApiOperation("删除视频")
	public Result removeVod(@PathVariable String id) {
		vodService.removeVod(id);
		return Result.ok().message("删除成功");
	}
}
