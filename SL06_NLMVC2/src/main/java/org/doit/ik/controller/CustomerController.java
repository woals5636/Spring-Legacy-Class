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
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping("noticeDel.htm")
	public String noticeDel(@RequestParam(value = "seq") String seq ) throws Exception{		

		int rowCount = this.noticeDao.delete(seq);
		
		if(rowCount==1) {
			return "redirect:notice.htm";
		}else {
			return "redirect:noticeDetail.htm?seq="+seq+"&error";
		}// if
	}
	
	// http://localhost/customer/noticeDetail.htm?seq=4
	// <input type="submit" value="수정" class="btn-save button"/>
	@PostMapping("noticeEdit.htm")
	public String noticeEdit(NoticeVO notice, Model model) throws Exception{		

		int rowCount = this.noticeDao.update(notice);
		
		if(rowCount==1) {
			return "redirect:noticeDetail.htm?seq="+notice.getSeq();
		}else {
			return "redirect:notice.htm";
		}// if
	}
	
	// <a class="btn-edit button" href="noticeEdit.htm">수정</a>
	@GetMapping("noticeEdit.htm")
	public String noticeEdit(@RequestParam("seq") String seq, Model model) throws Exception{		
		
		model.addAttribute("notice", this.noticeDao.getNotice(seq));
		
		return "noticeEdit.jsp";
	}
	
	
	// <input class="btn-save button" type="submit" value="저장" />
	//	<form action="" method="post">
	@PostMapping( value = "/noticeReg.htm" )
	public String noticeReg( NoticeVO notice ) throws Exception {	// 커맨드객체
		int rowCount = this.noticeDao.insert(notice);
		
		if(rowCount==1) {
			//글목록 페이지 이동 리다이렉트
			return "redirect:notice.htm";
		}else {
			// 글쓰기 페이지 이동 포워딩
			return "noticeReg.htm?error";
		}// if
	}
	
	//	<a class="btn-write button" href="noticeReg.htm">글쓰기</a>
	@GetMapping("/noticeReg.htm")
	public String noticeReg() {
		// 스프링 시큐리티(보안)으로 인증+권한 처리 예정
		return "noticeReg.jsp";
	}
	
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
