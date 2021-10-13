package com.online.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.online.edu.entity.ExcelSubject;
import com.online.edu.entity.Subject;
import com.online.edu.listener.SubjectExcelListener;
import com.online.edu.mapper.SubjectMapper;
import com.online.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

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
}
