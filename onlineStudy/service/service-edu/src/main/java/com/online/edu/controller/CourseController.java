package com.online.edu.controller;


import com.online.commonutils.Result;
import com.online.edu.entity.vo.CourseInfoVo;
import com.online.edu.service.CourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}

