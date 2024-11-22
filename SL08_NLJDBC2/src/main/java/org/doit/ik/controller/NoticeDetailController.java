package org.doit.ik.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.doit.ik.domain.NoticeVO;
import org.doit.ik.persistence.NoticeDaoImpl;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

// 공지사항 상세보기 컨트롤러
public class NoticeDetailController implements Controller{

	private NoticeDaoImpl noticeDaoImpl;

	public NoticeDetailController() {
	}

	public NoticeDetailController(NoticeDaoImpl noticeDaoImpl) {
		super();
		this.noticeDaoImpl = noticeDaoImpl;
	}

	// 공지사항 목록 요청 URL
	// http://localhost/customer/noticeDetail.htm?seq=1
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 리턴 자료형 : ModelAndView p.284
		ModelAndView mav = new ModelAndView("noticeDetail.jsp");
		String seq = request.getParameter("seq");
		NoticeVO notice = this.noticeDaoImpl.getNotice(seq);
		mav.addObject("notice",notice);
		
		return mav;
	}

}// class
