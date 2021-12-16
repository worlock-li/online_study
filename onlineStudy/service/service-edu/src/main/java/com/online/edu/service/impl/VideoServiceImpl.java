package com.online.edu.service.impl;

import com.online.edu.entity.Video;
import com.online.edu.mapper.VideoMapper;
import com.online.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author worlock
 * @since 2021-10-27
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

	@Resource
	private VideoMapper videoMapper;

	@Override
	public List<String> getVideoSourceIdByCourseId(String courseId) {
		return videoMapper.getVideoSourceIdByCourseId(courseId);
	}
}
