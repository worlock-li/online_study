package com.online.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.commonutils.Result;
import com.online.edu.client.VodClient;
import com.online.edu.entity.Chapter;
import com.online.edu.entity.Course;
import com.online.edu.entity.CourseDescription;
import com.online.edu.entity.Video;
import com.online.edu.entity.vo.CourseInfoVo;
import com.online.edu.entity.vo.CoursePublishVo;
import com.online.edu.mapper.CourseMapper;
import com.online.edu.service.ChapterService;
import com.online.edu.service.CourseDescriptionService;
import com.online.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.edu.service.VideoService;
import com.online.servicebase.exceptionhandler.OnlineException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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

	// 构造器注入
	private CourseDescriptionService courseDescriptionService;
	@Autowired
	public CourseServiceImpl(CourseDescriptionService courseDescriptionService) {
		this.courseDescriptionService = courseDescriptionService;
	}

	@Autowired
	ChapterService chapterService;
	@Autowired
	VideoService videoService;
	@Resource
	CourseMapper courseMapper;
	@Autowired
	VodClient vodClient;


	@Override
	public String addCourseInfo(CourseInfoVo courseInfoVo) {

		// 添加基本信息表中插入数据
		Course course = new Course();
		BeanUtils.copyProperties(courseInfoVo, course);
		course.setStatus("Draft");
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

	/**
	 * 根据id获取
	 * @param id
	 * @return
	 */
	@Override
	@Transactional
	public CourseInfoVo getCourseInfoById(String id) {
		// 获取课程基本信息
		Course course = courseMapper.selectById(id);
		CourseInfoVo courseInfoVo = new CourseInfoVo();
		BeanUtils.copyProperties(course, courseInfoVo);

		// 获取课程简介 1-1
		CourseDescription description = courseDescriptionService.getById(id);
		courseInfoVo.setDescription(description.getDescription());
		return courseInfoVo;
	}

	@Override
	public String updateByVo(CourseInfoVo courseInfoVo) {
		// 修改课程基本信息
		Course course = new Course();
		BeanUtils.copyProperties(courseInfoVo, course);
		int update = baseMapper.updateById(course);
		if (update == 0) {
			// 修改失败
			throw new OnlineException(20001, "修改失败");
		}

		// 修改课程简介
		CourseDescription courseDescription = new CourseDescription();
		// id在数据库设计中两张表是一样的
		courseDescription.setId(courseInfoVo.getId());
		courseDescription.setDescription(courseInfoVo.getDescription());
		courseDescriptionService.updateById(courseDescription);

		return courseInfoVo.getId();
	}

	@Override
	public CoursePublishVo getCourseInfoVo(String id) {
		return baseMapper.getCourseInfoVo(id);
	}

	@Override
	@Transactional
	public void removeCourse(String id) {
		// 删除课程
		baseMapper.deleteById(id);

		// 删除描述
		courseDescriptionService.removeById(id);

		// 删除章节
		QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
		chapterQueryWrapper.eq("course_id", id);
		List<Chapter> list = chapterService.list(chapterQueryWrapper);
		if (list.size() > 0) {
			chapterService.removeById(list.get(0).getId());
		}

		// 删除小节
		// 先删除小节里面的所有视频
		// 根据课程id查询出所有的所有的视频id
		List<String> videoSourceIdList = videoService.getVideoSourceIdByCourseId(id);
		// 调用方法删除多个视频
		if (!videoSourceIdList.isEmpty()) {
			Result result = vodClient.removeBatchVod(videoSourceIdList);
			if (!result.isSuccess()) {
				throw new OnlineException(20001, "删除视频失败，执行了熔断器");
			}
		}
		QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
		videoQueryWrapper.eq("course_id", id);
		List<Video> list1 = videoService.list(videoQueryWrapper);
		if (list1.size() > 0) {
			videoService.removeById(list1.get(0).getId());
		}
	}
}
