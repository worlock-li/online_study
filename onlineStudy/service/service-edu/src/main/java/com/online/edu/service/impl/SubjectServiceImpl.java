package com.online.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.edu.entity.ExcelSubject;
import com.online.edu.entity.Subject;
import com.online.edu.entity.subject.OneSubject;
import com.online.edu.entity.subject.TwoSubject;
import com.online.edu.listener.SubjectExcelListener;
import com.online.edu.mapper.SubjectMapper;
import com.online.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author worlock
 * @since 2021-10-13
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

	/**
	 * 添加课程分类
	 * @param file
	 */
	@Override
	public void saveSubject(MultipartFile file, SubjectService subjectService) {
		try {
			InputStream is = file.getInputStream();
			EasyExcel.read(is, ExcelSubject.class, new SubjectExcelListener(subjectService)).sheet().doRead();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<OneSubject> getAllSubject() {

		// 查询一级课程分类
		QueryWrapper<Subject> oneWrapper = new QueryWrapper<>();
		oneWrapper.eq("parent_id", "0");
		List<Subject> oneSubjectList = this.baseMapper.selectList(oneWrapper);

		// 查询二级课程分类
		QueryWrapper<Subject> twoWrapper = new QueryWrapper<>();
		twoWrapper.ne("parent_id", "0");
		List<Subject> twoSubjectList = this.baseMapper.selectList(twoWrapper);

		// 创建返回对象
		List<OneSubject> resultSubjects = new ArrayList<>();

		for (Subject subject : oneSubjectList) {
			OneSubject oneSubject = new OneSubject();
			oneSubject.setId(subject.getId());
			oneSubject.setTitle(subject.getTitle());
			resultSubjects.add(oneSubject);
		}

		for (OneSubject oneSubject : resultSubjects) {
			String id = oneSubject.getId();
			for (Subject twoSubject : twoSubjectList) {
				String parentId = twoSubject.getParentId();
				if (id.equals(parentId)) {
					TwoSubject twoSubject1 = new TwoSubject();
					twoSubject1.setId(twoSubject.getId());
					twoSubject1.setTitle(twoSubject.getTitle());
					oneSubject.getChildren().add(twoSubject1);
				}
			}
		}


		return resultSubjects;
	}
}
