package com.online.edu.service;

import com.online.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author worlock
 * @since 2021-10-27
 */
public interface ChapterService extends IService<Chapter> {

	List<ChapterVo> getChapterVideoByCourseId(String courseId);
}
