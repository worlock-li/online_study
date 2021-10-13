package com.online.edu.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcelSubject {
	@ExcelProperty(index = 0)
	private String oneSbjName;
	@ExcelProperty(index = 1)
	private String twoSbjName;
}
