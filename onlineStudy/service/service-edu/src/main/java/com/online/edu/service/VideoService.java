package com.online.edu.service;

import com.online.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author worlock
 * @since 2021-10-27
 */
public interface VideoService extends IService<Video> {

	List<String> getVideoSourceIdByCourseId(String courseId);
}
