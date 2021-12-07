package com.online.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.commonutils.Result;
import com.online.edu.entity.Course;
import com.online.edu.entity.Teacher;
import com.online.edu.entity.vo.CourseInfoVo;
import com.online.edu.entity.vo.CoursePublishVo;
import com.online.edu.service.CourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

	@GetMapping("/getCoursePublish/{id}")
	@ApiOperation("根据id获取课程的相关信息，基本信息、课程简介、课程讲师、课程分类")
	public Result getCoursePublish(@PathVariable String id) {
		CoursePublishVo courseInfoMap = courseService.getCourseInfoVo(id);
		return Result.ok().data("coursePublishVo", courseInfoMap);
	}

	@GetMapping("/coursePublish/{id}")
	@ApiOperation("课程最终发布")
	public Result coursePublish(@PathVariable String id) {
		Course course = new Course();
		course.setId(id);
		// 课程状态 Draft未发布  Normal已发布
		course.setStatus("Normal");
		courseService.updateById(course);
		return Result.ok().message("课程最终发布成功！");
	}

	@GetMapping("/delete/{id}")
	@ApiOperation("根据id获取")
	public Result delete(@PathVariable String id) {
		// TODO 关联删除、删除视频
		courseService.removeCourse(id);
		return Result.ok().message("删除课程成功");
	}

	@PostMapping("/coursePage/{pageNum}/{pageSize}")
	@ApiOperation("分页查询")
	public Result coursePage(@RequestBody Course course, @PathVariable long pageNum, @PathVariable long pageSize) {
		Page<Course> page = new Page<Course>(pageNum, pageSize);
		QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();

		if (course.getTitle() != null) {
			courseQueryWrapper.like("title", course.getTitle());
		}
		if (course.getStatus() != null) {
			courseQueryWrapper.like("status", course.getStatus());
		}

		courseService.page(page, courseQueryWrapper);
		return Result.ok().data("page", page);
	}


}

