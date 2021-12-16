package com.online.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {

	String vodUpload(MultipartFile file);

	void removeVod(String id);

	void removeBatchVod(List<String> vodId);
}
