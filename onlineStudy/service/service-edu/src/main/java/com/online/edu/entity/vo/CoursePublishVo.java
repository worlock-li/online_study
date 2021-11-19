package com.online.edu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CoursePublishVo {
	@ApiModelProperty(value = "课程标题")
	private String title;

	@ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
	private Double price;

	@ApiModelProperty(value = "总课时")
	private Integer lessonNum;

	@ApiModelProperty(value = "课程简介")
	private String description;

	@ApiModelProperty(value = "讲师姓名")
	private String teacherName;

	@ApiModelProperty(value = "二级分类")
	private String twoSubject;

	@ApiModelProperty(value = "一级分类")
	private String oneSubject;
}
