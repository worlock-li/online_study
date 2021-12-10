package com.online.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.online.vod.properties.VodProperties;
import com.online.vod.service.VodService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class VodServiceImpl implements VodService {
	/**
	 * 流式文件上传
	 * @param file
	 * @return
	 */
	@Override
	public String vodUpload(MultipartFile file) {
		// getOriginalFilename() 获取文件的实际名字
		String title = file.getOriginalFilename() + "-upload";
		String fileName = file.getOriginalFilename();
		InputStream inputStream = null;
		try {
			inputStream = file.getInputStream();
			UploadStreamRequest request = new UploadStreamRequest(VodProperties.KEY_ID, VodProperties.KEY_SECRET, title, fileName, inputStream);
			UploadVideoImpl uploader = new UploadVideoImpl();
			UploadStreamResponse response = uploader.uploadStream(request);
			//请求视频点播服务的请求ID
			System.out.print("RequestId=" + response.getRequestId() + "\n");
			if (response.isSuccess()) {
				return response.getVideoId();
			} else {
				//如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
				// 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
				return response.getVideoId();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
