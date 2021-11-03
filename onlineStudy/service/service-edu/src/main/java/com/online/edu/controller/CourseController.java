package com.online.edu.controller;


import com.online.commonutils.Result;
import com.online.edu.entity.vo.CourseInfoVo;
import com.online.edu.service.CourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 * 课程基本信息、简介
 * @author worlock
 * @since 2021-10-27
 */
@RestController
@RequestMapping("/eduService/course")
@CrossOrigin
public class CourseController {

	@Autowired
	private CourseService courseService;

	@PostMapping("/addCourseInfo")
	@ApiOperation("添加课程数据")
	public Result addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
		// 添加课程数据
		String id = courseService.addCourseInfo(courseInfoVo);
		return Result.ok().message("添加成功!").data("id", id);
	}


	@GetMapping("/getCourseInfoById/{id}")
	@ApiOperation("根据id获取")
	public Result getCourseInfoById(@PathVariable String id) {
		CourseInfoVo courseInfoVo = courseService.getCourseInfoById(id);
		return Result.ok().data("courseInfoVo", courseInfoVo);
	}

	@PostMapping("/update")
	@ApiOperation("修改课程信息")
	public Result update(@RequestBody CourseInfoVo courseInfoVo) {
		String id = courseService.updateByVo(courseInfoVo);
		return Result.ok().data("id", id);
	}

}

