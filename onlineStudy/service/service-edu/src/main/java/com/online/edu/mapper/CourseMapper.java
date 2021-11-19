package com.online.edu.mapper;

import com.online.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.online.edu.entity.vo.CoursePublishVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author worlock
 * @since 2021-10-27
 */
public interface CourseMapper extends BaseMapper<Course> {

	CoursePublishVo getCourseInfoVo(String id);
}
