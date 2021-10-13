package com.mydemo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class EasyExcelTest {
	public static void main(String[] args) {
		String fileName = "C:\\Users\\li\\Desktop\\easy.xlsx";

//		EasyExcel.write(fileName, MyData.class).sheet("数据").doWrite(getData());
		EasyExcel.read(fileName, MyData.class, new ExcelListener()).sheet().doRead();

	}

	public static List<MyData> getData() {
		List<MyData> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			MyData myData = new MyData();
			myData.setId(i);
			myData.setName("tom" + i);
			list.add(myData);
		}
		return list;
	}
}
