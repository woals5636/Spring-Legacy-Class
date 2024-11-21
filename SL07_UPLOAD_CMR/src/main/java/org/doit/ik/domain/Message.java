package org.doit.ik.domain;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import lombok.Data;

@Data
public class Message { // NoticeVO
	
	// <input type="text" name="output">
	private String output;
	// <input type="file" name="attach">
	private CommonsMultipartFile attach;
}
