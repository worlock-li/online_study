package com.online.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.edu.entity.ExcelSubject;
import com.online.edu.entity.Subject;
import com.online.edu.service.SubjectService;
import com.online.servicebase.exceptionhandler.OnlineException;

public class SubjectExcelListener extends AnalysisEventListener<ExcelSubject> {

	// 在使用 SubjectExcelListener 的时候，使用的是new，不能交给spring管理
	// 使用spring的东西需要手动传递
	public SubjectService subjectService;
	public SubjectExcelListener() {
	}
	public SubjectExcelListener(SubjectService subjectService) {
		this.subjectService = subjectService;
	}


	@Override
	public void invoke(ExcelSubject excelSubject, AnalysisContext analysisContext) {
		if (excelSubject == null) {
			throw new OnlineException(20001, "数据为空");
		}

		Subject oneSubject = this.isHaveSubject(subjectService, excelSubject.getOneSbjName(), "0");
		if (oneSubject == null) {
			// 保存一级分类
			oneSubject = new Subject();
			oneSubject.setTitle(excelSubject.getOneSbjName());
			oneSubject.setParentId("0");
			subjectService.save(oneSubject);
		}
		// 添加成功后， mybatis-plus会自动填充
		Subject twoSubject = this.isHaveSubject(subjectService, excelSubject.getTwoSbjName(), oneSubject.getId());
		if (twoSubject == null) {
			// 保存二级分类
			twoSubject = new Subject();
			twoSubject.setTitle(excelSubject.getTwoSbjName());
			twoSubject.setParentId(oneSubject.getId());
			subjectService.save(twoSubject);
		}

	}

	/**
	 * 判断数据库中有没有二级分类
	 * @param name
	 * @return
	 */
	private Subject isHaveSubject(SubjectService subjectService, String name, String parentId) {
		QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("title", name);
		queryWrapper.eq("parent_id", parentId);
		return subjectService.getOne(queryWrapper);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {

	}
}
