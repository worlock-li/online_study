package com.online.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.online.servicebase.exceptionhandler.OnlineException;
import com.online.vod.handler.InitClient;
import com.online.vod.properties.VodProperties;
import com.online.vod.service.VodService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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

	@Override
	public void removeVod(String id) {
		DefaultAcsClient client = InitClient.initVodClient(VodProperties.KEY_ID, VodProperties.KEY_SECRET);
		// 获取request
		DeleteVideoRequest request = new DeleteVideoRequest();
		//支持传入多个视频ID，多个用逗号分隔
		request.setVideoIds(id);

		try {
			// 调用client里面的方法删除视频
			client.getAcsResponse(request);
		} catch (Exception e) {
			System.out.print("ErrorMessage = " + e.getLocalizedMessage());
			throw new OnlineException(20001, "删除视频失败");
		}
	}

	@Override
	public void removeBatchVod(List<String> vodId) {
		DefaultAcsClient client = InitClient.initVodClient(VodProperties.KEY_ID, VodProperties.KEY_SECRET);
		// 获取request
		DeleteVideoRequest request = new DeleteVideoRequest();
		//支持传入多个视频ID，多个用逗号分隔
		request.setVideoIds(StringUtils.join(vodId, ","));

		try {
			// 调用client里面的方法删除视频
			client.getAcsResponse(request);
		} catch (Exception e) {
			System.out.print("ErrorMessage = " + e.getLocalizedMessage());
			throw new OnlineException(20001, "删除视频失败");
		}
	}
}
