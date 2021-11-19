package com.online.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.commonutils.Result;
import com.online.edu.entity.Chapter;
import com.online.edu.entity.Video;
import com.online.edu.entity.chapter.ChapterVo;
import com.online.edu.service.ChapterService;
import com.online.edu.service.VideoService;
import com.online.servicebase.exceptionhandler.OnlineException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *	课程章节
 * @author worlock
 * @since 2021-10-27
 */
@RestController
@RequestMapping("/eduService/chapter")
@CrossOrigin
public class ChapterController {

	@Autowired
	private ChapterService chapterService;

	@Autowired
	private VideoService videoService;

	/**
	 * 根据课程id获取章节
	 * @param courseId
	 * @return
	 */
	@GetMapping("/getChapterList/{courseId}")
	@ApiOperation("根据课程id获取章节")
	public Result getChapterList(@PathVariable String courseId) {
		List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
		return Result.ok().data("list", list);
	}

	/**
	 * 添加章节
	 * @param chapter
	 * @return
	 */
	@PostMapping("/add")
	@ApiOperation("添加章节")
	public Result addChapter(@RequestBody Chapter chapter) {
		chapterService.save(chapter);
		return Result.ok().message("添加成功");
	}

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	@GetMapping("/getChapterById/{id}")
	@ApiOperation("根据id查询")
	public Result getChapterById(@PathVariable String id) {
		Chapter chapter = chapterService.getById(id);
		return Result.ok().data("chapter", chapter);
	}

	/**
	 * 修改
	 * @param chapter
	 * @return
	 */
	@PostMapping("/update")
	@ApiOperation("修改")
	public Result update(@RequestBody Chapter chapter) {
		chapterService.updateById(chapter);
		return Result.ok().message("修改成功");
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@DeleteMapping("/delete/{id}")
	@ApiOperation("删除")
	public Result delete(@PathVariable String id) {
		// 如果当前章节下有小节，不能删除
		QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
		videoQueryWrapper.eq("chapter_id", id);
		// 查询小节数量
		int count = videoService.count(videoQueryWrapper);
		if (count > 0) {
			// 有小节，不能删除
			throw new OnlineException(20001, "该章节下有小节，不能删除");
		} else {
			chapterService.removeById(id);
			return Result.ok().message("删除成功");
		}
	}


}

