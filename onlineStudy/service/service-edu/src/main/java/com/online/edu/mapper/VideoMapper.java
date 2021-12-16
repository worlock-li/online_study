package com.online.edu.mapper;

import com.online.edu.entity.Video;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author worlock
 * @since 2021-10-27
 */
public interface VideoMapper extends BaseMapper<Video> {

	List<String> getVideoSourceIdByCourseId(String courseId);
}
