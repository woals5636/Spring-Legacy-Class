package org.doit.ik.domain;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import lombok.Data;

@Data
public class MultiMessage { // NoticeVO
	
	// <input type="text" name="output">
	private String output;
	// <input type="file" name="attach">
	// private CommonsMultipartFile [] attach;
	private List<CommonsMultipartFile> attach;
}
