package com.online.edu.service;

import com.online.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author worlock
 * @since 2021-10-13
 */
public interface SubjectService extends IService<Subject> {

	void saveSubject(MultipartFile file, SubjectService subjectService);

	List<OneSubject> getAllSubject();
}
