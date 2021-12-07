package com.online.edu.service;

import com.online.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.entity.vo.CourseInfoVo;
import com.online.edu.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author worlock
 * @since 2021-10-27
 */
public interface CourseService extends IService<Course> {

	String addCourseInfo(CourseInfoVo courseInfoVo);

	CourseInfoVo getCourseInfoById(String id);

	String updateByVo(CourseInfoVo courseInfoVo);

	CoursePublishVo getCourseInfoVo(String id);

	void removeCourse(String id);
}
