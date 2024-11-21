package org.doit.ik.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.doit.ik.domain.NoticeVO;
import org.doit.ik.persistence.NoticeDao;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

// 공지사항 상세보기 컨트롤러
public class NoticeDetailController implements Controller{

	private NoticeDao noticeDao;

	public NoticeDetailController() {
	}

	public NoticeDetailController(NoticeDao noticeDao) {
		super();
		this.noticeDao = noticeDao;
	}

	// 공지사항 목록 요청 URL
	// http://localhost/customer/noticeDetail.htm?seq=1
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 리턴 자료형 : ModelAndView p.284
		ModelAndView mav = new ModelAndView("noticeDetail.jsp");
		String seq = request.getParameter("seq");
		NoticeVO notice = this.noticeDao.getNotice(seq);
		mav.addObject("notice",notice);
		
		return mav;
	}

}// class
