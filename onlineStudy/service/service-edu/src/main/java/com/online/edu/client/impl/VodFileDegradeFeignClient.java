package com.online.edu.client.impl;

import com.online.commonutils.Result;
import com.online.edu.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
	@Override
	public Result removeVod(String id) {
		return Result.error().message("删除视频出错了，兜底方法执行！");
	}

	@Override
	public Result removeBatchVod(List<String> vodIdList) {
		return Result.error().message("删除多个视频出错了，兜底方法执行！");
	}
}
