package com.online.edu.controller;


import com.online.commonutils.Result;
import com.online.edu.entity.Video;
import com.online.edu.service.VideoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *  小节
 * @author worlock
 * @since 2021-10-27
 */
@RestController
@RequestMapping("/eduService/video")
@CrossOrigin
public class VideoController {

	@Autowired
	private VideoService videoService;

	@PostMapping("/add")
	@ApiOperation("添加小节")
	public Result add(@RequestBody Video video) {
		videoService.save(video);
		return Result.ok().message("添加小节成功！");
	}

	@DeleteMapping("/delete/{id}")
	@ApiOperation("删除小节")
	public Result delete(@PathVariable String id) {
		videoService.removeById(id);
		return Result.ok().message("删除小节成功!");
	}

	@PostMapping("/update")
	@ApiOperation("修改小节")
	public Result delete(@RequestBody Video video) {
		videoService.updateById(video);
		// 删除小节的同时，需要把里面的视频也删除
		return Result.ok().message("修改小节成功!");
	}

	@GetMapping("/getById/{id}")
	@ApiOperation("根据id获取小节")
	public Result getById(@PathVariable String id) {
		Video video = videoService.getById(id);
		return Result.ok().data("video", video);
	}

}

