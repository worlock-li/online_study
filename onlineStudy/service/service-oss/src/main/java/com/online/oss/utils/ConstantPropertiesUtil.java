package com.online.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 读取配置文件中的内容
 * 实现的 InitializingBean接口
 * 在项目初始化的时候，加载这个类
 * 在加载完成数据时，执行 afterPropertiesSet() 方法
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {
	@Value("${aliyun.oss.file.endpoint}")
	private String endpoint;

	@Value("${aliyun.oss.file.keyid}")
	private String keyId;

	@Value("${aliyun.oss.file.keysecret}")
	private String keySecret;

	@Value("${aliyun.oss.file.bucketname}")
	private String bucketName;

	public static String END_POINT;
	public static String KEY_ID;
	public static String KEY_SECRET;
	public static String BUCKET_NAME;

	@Override
	public void afterPropertiesSet() throws Exception {
		END_POINT = endpoint;
		KEY_ID = keyId;
		KEY_SECRET = keySecret;
		BUCKET_NAME = bucketName;
	}
}
