package com.online.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.edu.entity.Chapter;
import com.online.edu.entity.Video;
import com.online.edu.entity.chapter.ChapterVo;
import com.online.edu.entity.chapter.VideoVo;
import com.online.edu.mapper.ChapterMapper;
import com.online.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

	@Autowired
	private VideoService videoService;

	@Override
	public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

		// 第一步：获取所有的章节
		QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
		// 根据课程id获取所有的章节
		chapterQueryWrapper.eq("course_id", courseId);
		List<Chapter> chapterList = baseMapper.selectList(chapterQueryWrapper);

		// 第二部：获取所有的小节
		QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
		// 根据课程id获取所有的小节
		videoQueryWrapper.eq("course_id", courseId);
		List<Video> videoList = videoService.list(videoQueryWrapper);

		// 创建一个最终返回的list集合
		List<ChapterVo> finalList = new ArrayList<>();

		// 第三步：对所有的章节数据进行封装
		for (Chapter chapter : chapterList) {
			ChapterVo chapterVo = new ChapterVo();
			BeanUtils.copyProperties(chapter, chapterVo);

			// 小节list
			List<VideoVo> videoVoList = new ArrayList<>();

			// 第四步：对所有的小节数据进行封装
			for (Video video : videoList) {
				// 判断是否是当前章节的小节
				if (video.getChapterId().equals(chapter.getId())) {
					VideoVo videoVo = new VideoVo();
					BeanUtils.copyProperties(video, videoVo);
					videoVoList.add(videoVo);
				}
			}
			chapterVo.setChildren((ArrayList) videoVoList);
			finalList.add(chapterVo);
		}

		return finalList;
	}
}
