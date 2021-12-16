package com.online.edu.client;

import com.online.commonutils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient("server-vod") // 设置需要调用的服务端的名称
public interface VodClient {

	@DeleteMapping("/vod/video/removeVod/{id}") // 完整路径
	public Result removeVod(@PathVariable("id") String id); // @PathVariable("id")必须写

	@DeleteMapping("/vod/video/removeBatchVod")
	public Result removeBatchVod(@RequestParam("vodIdList") List<String> vodIdList);
}
