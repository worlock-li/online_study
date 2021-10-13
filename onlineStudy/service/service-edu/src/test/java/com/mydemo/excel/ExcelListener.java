package com.mydemo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

public class ExcelListener extends AnalysisEventListener {
	// 一行行读取excel中的数据
	@Override
	public void invoke(Object o, AnalysisContext analysisContext) {
		System.out.println(">>>>>" + o);
	}

	// 完成之后的操作
	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {

	}

}
