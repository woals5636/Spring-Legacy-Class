package org.doit.ik.controller;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.doit.ik.domain.NoticeVO;
import org.doit.ik.persistence.NoticeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;

// 공지사항
@Controller
@RequestMapping("/customer/*")
@AllArgsConstructor
public class CustomerController {
	
	private NoticeDao noticeDao;
	
	// 공지사항 목록 요청 URL
	// http://localhost/customer/notice.htm?page=2&field=검색조건&query=검색어
	@GetMapping("/notice.htm")
	public String notices( Model model
						, @RequestParam(value = "page", defaultValue = "1") int page 
						, @RequestParam(value = "field", defaultValue = "title") String field
						, @RequestParam(value = "query", defaultValue = "") String query 
							) throws Exception {
		List<NoticeVO> list = this.noticeDao.getNotices(page, field, query);

		model.addAttribute("list",list);
		model.addAttribute("message","Hello World");
		
		return "notice.jsp";
	}

	// 공지사항 목록 요청 URL
	// http://localhost/customer/noticeDetail.htm?seq=1
	@GetMapping("/noticeDetail.htm")
	public String noticeDetail(  Model model
							, @RequestParam(value = "seq", defaultValue = "1") String seq 
								) throws Exception {
		
		NoticeVO notice = this.noticeDao.getNotice(seq);

		model.addAttribute("notice", notice);
		
		return "noticeDetail.jsp";
	}
	
}
