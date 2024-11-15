package org.doit.ik;

import org.doit.ik.domain.BoardVO;
import org.doit.ik.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@AllArgsConstructor
@RequestMapping("/board/*")
public class BoardController {
	
	private BoardService boardService;
/*	[1]
	@GetMapping("/board/list")
	public String list(Model model) {
		// log.info(log);  == // private static final Logger logger = LoggerFactory.getLogger(BoardController.class); 같은 코딩임
		log.info("> BoardController.list() ...");
		
		model.addAttribute("list", this.boardService.getList());
		
		return "/board/list";
	}
*/
	// [2]
	// 요청 URL 과 같을 경우에는 return 생략 가능 ( void )
	@GetMapping("/list")
	public void list(Model model) {
		// log.info(log);  == // private static final Logger logger = LoggerFactory.getLogger(BoardController.class); 같은 코딩임
		log.info("> BoardController.list() ...");
		
		model.addAttribute("list", this.boardService.getList());
		
	}
/*	[3]
	@GetMapping("/board/list")
	public ModelAndView list(ModelAndView mav) {
		// log.info(log);  == // private static final Logger logger = LoggerFactory.getLogger(BoardController.class); 같은 코딩임
		log.info("> BoardController.list() ...");
		
		mav.addObject("list", this.boardService.getList());
		mav.setViewName("/board/list");
		return mav;
	}
*/
	
	// <a href="/board/register">글쓰기</a>
	@GetMapping("/register")
	public void register() {
		log.info("> BoardController.register() ...");
	}

	// <button type="submit">Submit</button>
	@PostMapping("/register")
	public String register( BoardVO board , RedirectAttributes rttr ) {
		log.info("> BoardController.register() POST...");
		this.boardService.register(board);
		rttr.addFlashAttribute("result", board.getBno());
		return "redirect:/board/list";
	}

	@PostMapping(value={"/modify"})
	public String modify( BoardVO boardVO, RedirectAttributes rttr) {
		log.info("> BoardController.modify() POST...");
		
		if( this.boardService.modify(boardVO)) {
			rttr.addFlashAttribute("result","SUCCESS");
		} // if
		
		return String.format("redirect:/board/get?bno=%d", boardVO.getBno());
	}
	
	@GetMapping(value={"/remove"})
	public String remove( @RequestParam("bno") Long bno, RedirectAttributes rttr) {
		log.info("> BoardController.remove()...");
		
		if( this.boardService.remove(bno)) {
			rttr.addFlashAttribute("result","SUCCESS");
			rttr.addFlashAttribute("del",bno);
		} // if
		
		return String.format("redirect:/board/list");
	}
	
	 // <a href="/board/get?bno=${ board.bno }"></a>
	@GetMapping(value={"/get", "/modify"})
	public void get(@RequestParam("bno") Long bno, Model model) {
		log.info("> BoardController.get() ...");
		model.addAttribute("boardVO", this.boardService.get(bno));
	}
		
/*
	// <a href="/board/get/${ board.bno }"></a>
	@GetMapping("/get/{bno}")
	public String get(@PathVariable("bno") Long bno, Model model ) {
		log.info("> BoardController.get() POST...");
		this.boardService.get(bno);
		return "/board/get";
		
	}
*/
}
