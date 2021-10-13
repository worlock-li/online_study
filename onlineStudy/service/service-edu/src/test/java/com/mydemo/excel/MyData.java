package com.mydemo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class MyData {
	@ExcelProperty("学生id")
	private int id;
	@ExcelProperty("学生姓名")
	private String name;
}
