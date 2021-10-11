package com.online.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.online.oss.service.OssService;
import com.online.oss.utils.ConstantPropertiesUtil;
import jdk.internal.util.xml.impl.Input;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class OssServiceImpl implements OssService {

	@Autowired
	ConstantPropertiesUtil constantPropertiesUtil;
	/**
	 * 上传文件到oss
	 * @param file
	 * @return
	 */
	@Override
	public String uploadOssAvatar(MultipartFile file) {

		String url = "";
		try {
			// 获取文件真实名称
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

			String filename = file.getOriginalFilename();
			int of = filename.lastIndexOf(".");
			String name = filename.substring(0 ,of);
			String end = filename.substring(of);
			filename = (name + "-" + sdf.format(date) + end);

			// yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，
			// Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
			String endpoint = ConstantPropertiesUtil.END_POINT;
			// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，
			// 请登录RAM控制台创建RAM用户。
			String accessKeyId = ConstantPropertiesUtil.KEY_ID;
			String accessKeySecret = ConstantPropertiesUtil.KEY_SECRET;
			String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

			// 创建OSSClient实例。
			OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

			// 创建PutObjectRequest对象。
			// 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。- 上传到oss的文件路径及名称
			// Object完整路径中不能包含Bucket名称。
			InputStream inputStream = file.getInputStream();
			ossClient.putObject(bucketName, filename, inputStream);

			url = "https://" + bucketName + "." + endpoint + "/" + filename;
			ossClient.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return url;
	}
}
