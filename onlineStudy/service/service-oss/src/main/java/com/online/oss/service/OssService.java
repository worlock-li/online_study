package com.online.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {

	/**
	 * 上传文件
 	 */
	String uploadOssAvatar(MultipartFile file);
}
