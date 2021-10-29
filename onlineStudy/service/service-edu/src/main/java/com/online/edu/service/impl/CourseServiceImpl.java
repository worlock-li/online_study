package com.online.edu.service.impl;

import com.online.edu.entity.Course;
import com.online.edu.entity.CourseDescription;
import com.online.edu.entity.vo.CourseInfoVo;
import com.online.edu.mapper.CourseMapper;
import com.online.edu.service.CourseDescriptionService;
import com.online.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.servicebase.exceptionhandler.OnlineException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author worlock
 * @since 2021-10-27
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

	@Autowired
	CourseDescriptionService courseDescriptionService;

	@Override
	public String addCourseInfo(CourseInfoVo courseInfoVo) {

		// 添加基本信息表中插入数据
		Course course = new Course();
		BeanUtils.copyProperties(courseInfoVo, course);
		int insert = baseMapper.insert(course);
		if (insert == 0) {
			throw new OnlineException(20001, "添加课程失败");
		}

		// 获取基本信息id
		String id = course.getId();

		// 描述表中插入数据
		CourseDescription courseDescription = new CourseDescription();
		courseDescription.setDescription(courseInfoVo.getDescription());
		courseDescription.setId(id);
		courseDescriptionService.save(courseDescription);

		return id;
	}
}
