package com.online.vod.service;

import org.springframework.web.multipart.MultipartFile;

public interface VodService {

	String vodUpload(MultipartFile file);
}
