package org.doit.ik.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.doit.ik.domain.NoticeVO;
import org.doit.ik.persistence.NoticeDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

// 공지사항 
@Controller
@RequestMapping("/customer/*")
public class CustomerController {

	@Autowired
	private NoticeDaoImpl noticeDaoImpl;

	// 컨트롤러 메서드 선언
	// <a href="download.htm?dir=customer/upload&file=${ notice.filesrc  }">${ notice.filesrc }</a>
	//  download.htm
	//  ?
	//  dir=customer/upload    경로
	//  &
	//  file=다운로드할 파일명
	@RequestMapping("download.htm")
	public void download (
			HttpServletResponse response
			, HttpServletRequest request
			, @RequestParam("dir") String p
			, @RequestParam("file") String f
			) throws Exception{ 

		String fname =  f;  

		response.setHeader("Content-Disposition","attachment;filename="+ new String(fname.getBytes(), "ISO8859_1"));      

		String fullPath = request.getServletContext().getRealPath(   p + "/" + fname);

		FileInputStream fin = new FileInputStream(fullPath);
		ServletOutputStream sout = response.getOutputStream(); // 응답 스트림
		byte[] buf = new byte[1024];
		int size = 0;
		while((size = fin.read(buf, 0, 1024)) != -1) {
			sout.write(buf, 0, size); 
		}
		fin.close();
		sout.close();

	} // download

	// <a class="btn-del button" href="noticeDel.htm?seq=${ notice.seq }&filesrc=${ notice.filesrc }">삭제</a>
	@GetMapping("noticeDel.htm")
	public String noticeDel(@RequestParam("seq") String seq
			,@RequestParam("filesrc") String filesrc 
			, HttpServletRequest request) 
					throws ClassNotFoundException, SQLException {
		// 1. 첨부파일이 있는 공지사항이라면 첨부파일 폴더 삭제.
		// if( filesrc != null )          X
		// if(! filesrc.equals(""))       O
		String uploadRealPath = request.getServletContext().getRealPath("/customer/upload");
		File delFile = new File(uploadRealPath, filesrc);
		// System.out.println(">>> " + delFile.isDirectory() + " / " + delFile.isFile() );
		if (  delFile.exists()  && delFile.isFile()  ) {
			delFile.delete();
		} // if

		// 2. 
		int rowCount = this.noticeDaoImpl.delete(seq);
		if (rowCount == 1) { 
			return "redirect:notice.htm";
		} else { 
			return "redirect:noticeDetail.htm?seq="+seq+"&error";
		} // if
	}

	// <!-- http://localhost/customer/noticeEdit.htm?seq=3 -->
	// <input type="submit" value="수정"  class="btn-save button"/>
	// <form action="" method="post">
	@PostMapping("noticeEdit.htm")
	public String noticeEdit(NoticeVO notice, Model model
							, @RequestParam("o_filesrc") String  oFilesrc
							, HttpServletRequest request   ) throws Exception {
		// 1.
		CommonsMultipartFile multipartFile = notice.getFile();
		String uploadRealPath = null;
		if ( !multipartFile.isEmpty() ) {
			uploadRealPath = request.getServletContext().getRealPath("/customer/upload");
			System.out.println("> uploadRealPath : " + uploadRealPath);
			// ㄱ. 기존 첨부파일 삭제
			File delFile = new File(uploadRealPath, oFilesrc);
			// System.out.println(">>> " + delFile.isDirectory() + " / " + delFile.isFile() );
			if (  delFile.exists()  && delFile.isFile()  ) {
				delFile.delete();
			} // if
			
			// ㄴ. 새로운 첨부 파일 저장
			String  originalFilename = multipartFile.getOriginalFilename();
			String filesystemName = getFileNameCheck(uploadRealPath, originalFilename);

			File dest = new File(uploadRealPath, filesystemName );
			multipartFile.transferTo(dest); // 실제 파일 저장

			notice.setFilesrc(filesystemName);
		} else {	// 첨부파일 없는 경우
			notice.setFilesrc(oFilesrc);
		}// if

		// 2.
		int rowCount = this.noticeDaoImpl.update(notice);
		if (rowCount == 1) { 
			return "redirect:noticeDetail.htm?seq="+notice.getSeq();
		} else { 
			return "redirect:notice.htm";
		} // if    
	}

	//   <a class="btn-edit button" href="noticeEdit.htm?seq=${ notice.seq }">수정</a>
	@GetMapping("noticeEdit.htm")
	public String noticeEdit(@RequestParam("seq") String seq, Model model  ) 
			throws ClassNotFoundException, SQLException {
		NoticeVO notice = this.noticeDaoImpl.getNotice(seq);
		model.addAttribute("notice", notice);
		return "noticeEdit.jsp";
	}

	private String getFileNameCheck(String uploadRealPath, String originalFilename) {
		if (originalFilename == null || originalFilename.isEmpty()) {
			throw new IllegalArgumentException("Filename cannot be null or empty.");
		}

		int index = 1;

		while (true) {
			File file = new File(uploadRealPath, originalFilename);

			// 파일이 존재하지 않으면 현재 이름을 반환
			if (!file.exists()) {
				return originalFilename;
			}

			// 파일명과 확장자 분리
			int dotIndex = originalFilename.lastIndexOf(".");
			String fileName = (dotIndex == -1) ? originalFilename : originalFilename.substring(0, dotIndex);
			String extension = (dotIndex == -1) ? "" : originalFilename.substring(dotIndex);

			// 중복 방지를 위해 숫자 추가
			originalFilename = fileName + "-" + index + extension;

			index++;
		}
	}

	// <input class="btn-save button" type="submit" value="저장" />
	// <form action="" method="post">
	//          action = "http://localhost/customer/noticeReg.htm"
	@PostMapping( value =  "/noticeReg.htm" )
	public String noticeReg( NoticeVO notice , HttpServletRequest request ) 
			throws ClassNotFoundException, SQLException, IllegalStateException, IOException {   // 커맨드객체
		// 1.
		CommonsMultipartFile multipartFile = notice.getFile();
		String uploadRealPath = null;
		if ( !multipartFile.isEmpty() ) {
			uploadRealPath = request.getServletContext().getRealPath("/customer/upload");
			System.out.println("> uploadRealPath : " + uploadRealPath);

			//        a_3.txt
			String  originalFilename = multipartFile.getOriginalFilename();
			String filesystemName = getFileNameCheck(uploadRealPath, originalFilename);

			File dest = new File(uploadRealPath, filesystemName );
			multipartFile.transferTo(dest); // 실제 파일 저장

			notice.setFilesrc(filesystemName);
		} // if

		notice.setWriter("kenik");

		// 2.
		int rowCount = this.noticeDaoImpl.insert(notice);
		if (rowCount == 1) {
			// 글목록 페이지 이동  포워딩/리다이렉트
			return "redirect:notice.htm";
		} else {
			// 글쓰기 페이지 이동  포워딩
			return "noticeReg.htm?error";
		} // if      

	}
	/*
   @PostMapping( value =  "/noticeReg.htm" )
   public String noticeReg( @RequestParam("title") String title, 
         @RequestParam("content") String content, ) {
      NoticeVO notice = new NoticeVO();
      notice.setTitle(title);
      notice.setContent(content);

      return "noticeReg.jsp";
   }
	 */

	// <a class="btn-write button" href="noticeReg.htm">글쓰기</a>
	@GetMapping("/noticeReg.htm")
	public String noticeReg(HttpSession session) {
		// 스프링 시큐리티(보안) : 인증+권한 처리
		return "noticeReg.jsp";
	}

	// http://localhost/customer/noticeDetail.htm?seq=1
	@GetMapping("/noticeDetail.htm")
	public String noticeDetail(Model model
			, @RequestParam( value = "seq") String seq )
					throws ClassNotFoundException, SQLException {
		NoticeVO  notice  = this.noticeDaoImpl.getNotice(seq);      
		model.addAttribute("notice", notice);          
		return "noticeDetail.jsp";
	}

	/* [1]
   // http://localhost/customer/noticeDetail.htm?seq=1
   @Override
   public ModelAndView handleRequest(
         HttpServletRequest request
         , HttpServletResponse response) throws Exception {
      // 리턴자료형 : ModelAndView  p282
      ModelAndView mav = new ModelAndView("noticeDetail.jsp");      
      String seq = request.getParameter("seq");       
      NoticeVO  notice  = this.noticeDao.getNotice(seq);      
      mav.addObject("notice", notice);          
      return mav;
   }
	 */

	// 공지사항 목록 요청 URL 
	// http://localhost/customer/notice.htm?page=2&field=검색조건&query=검색어
	@GetMapping("/notice.htm")
	public String notices(Model model
			, @RequestParam( value = "page", defaultValue = "1") int page
			, @RequestParam( value = "field", defaultValue = "title") String field
			, @RequestParam( value = "query", defaultValue = "") String query
			) throws ClassNotFoundException, SQLException {
		List<NoticeVO> list = this.noticeDaoImpl.getNotices(page, field, query);
		model.addAttribute("list", list);
		model.addAttribute("message", "Hello World!");
		return "notice.jsp";
	}

	/* NoticeController.java 코딩.
   public ModelAndView handleRequest(
         HttpServletRequest request
         , HttpServletResponse response) throws Exception {
      // 리턴자료형 : ModelAndView  p282
      ModelAndView mav = new ModelAndView();

      String ppage = request.getParameter("page");
      String pfield = request.getParameter("field");
      String pquery = request.getParameter("query");

      int page = 1;
      String field = "title";
      String query = "";

      if( ppage != null && !ppage.equals("") ) page = Integer.parseInt(ppage);
      if( pfield != null && !pfield.equals("") ) field = pfield;
      if( pquery != null && !pquery.equals("") ) query = pquery;

      List<NoticeVO> list = this.noticeDao.getNotices(page, field, query);

      mav.addObject("list", list);
      mav.addObject("message", "Hello World!");

      mav.setViewName("notice.jsp");

      return mav;
   }
	 */
} // class






