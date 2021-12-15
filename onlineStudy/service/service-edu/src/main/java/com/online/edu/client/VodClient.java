package com.online.edu.client;

import com.online.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("server-vod") // 设置需要调用的服务端的名称
public interface VodClient {

	@DeleteMapping("/vod/video/removeVod/{id}") // 完整路径
	public Result removeVod(@PathVariable("id") String id); // @PathVariable("id")必须写
}
