package com.online.edu.entity.chapter;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * 章节
 */
@Data
public class ChapterVo {
	private String id;
	private String title;
	// 小节
	private ArrayList children = new ArrayList();
}
