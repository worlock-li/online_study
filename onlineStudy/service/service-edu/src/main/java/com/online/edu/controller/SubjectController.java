package com.online.edu.controller;


import com.online.commonutils.Result;
import com.online.edu.entity.subject.OneSubject;
import com.online.edu.service.SubjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author worlock
 * @since 2021-10-13
 */
@RestController
@CrossOrigin
@RequestMapping("/eduService/subject")
@Api("分类管理")
public class SubjectController {

	@Autowired
	SubjectService subjectService;

	@PostMapping("/upload")
	public Result addSubject(MultipartFile file) {
		subjectService.saveSubject(file, subjectService);
		return Result.ok();
	}

	@GetMapping("/getAllSubject")
	public Result getAllSubject() {
		List<OneSubject> allList = subjectService.getAllSubject();
		return Result.ok().data("data", allList);
	}


}

