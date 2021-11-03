package com.online.edu.controller;


import com.online.commonutils.Result;
import com.online.edu.entity.chapter.ChapterVo;
import com.online.edu.service.ChapterService;
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

	@GetMapping("getChapterList/{courseId}")
	public Result getChapterList(@PathVariable String courseId) {
		List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
		return Result.ok().data("list", list);
	}

}

