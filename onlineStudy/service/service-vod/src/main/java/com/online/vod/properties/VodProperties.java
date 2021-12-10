package com.online.vod.properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VodProperties implements InitializingBean {

	@Value("${aliyun.oss.file.keyid}")
	private String keyId;

	@Value("${aliyun.oss.file.keysecret}")
	private String keySecret;

	public static String KEY_ID;
	public static String KEY_SECRET;

	@Override
	public void afterPropertiesSet() {
		KEY_ID = keyId;
		KEY_SECRET = keySecret;
	}
}
