package org.doit.ik;

import org.doit.ik.domain.BoardVO;
import org.doit.ik.domain.Criteria;
import org.doit.ik.domain.PageDTO;
import org.doit.ik.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
/*
	// [2] 페이징 처리 안된 경우
	// 요청 URL 과 같을 경우에는 return 생략 가능 ( void )
	@GetMapping("/list")
	public void list(Model model) {
		// log.info(log);  == // private static final Logger logger = LoggerFactory.getLogger(BoardController.class); 같은 코딩임
		log.info("> BoardController.list() ...");
		
		model.addAttribute("list", this.boardService.getList());
		
	}
*/
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
	
	// 페이징 처리가 된 컨트롤러 메서드
	// http://localhost/board/list
	// http://localhost/board/list?pageNum=3&amount=10
	@GetMapping("/list")
	public void list(Model model , Criteria criteria) {
		log.info("> BoardController.list() ...");
		model.addAttribute("list", this.boardService.getListWithPaging(criteria));
		
		// 페이징 블럭	1 2 [3] 4 5 6 7 8 9 10 >
		int total = this.boardService.getTotal(criteria);
		model.addAttribute("pageMaker", new PageDTO(criteria,total));
	}
	
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
	public String modify( BoardVO boardVO, RedirectAttributes rttr, Criteria criteria) {
		log.info("> BoardController.modify() POST...");
		
		if( this.boardService.modify(boardVO)) {
			rttr.addFlashAttribute("result","SUCCESS");
		} // if

		/*
		rttr.addAttribute("pageNum", criteria.getPageNum());
		rttr.addAttribute("amount", criteria.getAmount());
		rttr.addAttribute("type", criteria.getType());
		rttr.addAttribute("keyword", criteria.getKeyword());
		*/
		
		String params = criteria.getListLink();
		// XXX : ?pageNum=1&amount=10&type=TC&keyword=%EC%9E%90%EB%B0%94
		// System.out.println("XXX : " + params);
		
		// return String.format("redirect:/board/get?bno=%d", boardVO.getBno());
		return String.format("redirect:/board/get?%S&bno=%d", params, boardVO.getBno());
		// return String.format("redirect:/board/get?bno=%d&pageNum=%d&amount=%d", boardVO.getBno(), criteria.getPageNum(), criteria.getAmount());
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
	
	// [3]
	// <a href="/board/get?bno=${ board.bno }"></a>
	@GetMapping(value={"/get", "/modify"})
	public void get(@RequestParam("bno") Long bno, Model model, @ModelAttribute("criteria") Criteria criteria) {
		log.info("> BoardController.get() ...");
		model.addAttribute("boardVO", this.boardService.get(bno));
	}
	
/*
 	// [2]	
	 // <a href="/board/get?bno=${ board.bno }"></a>
	@GetMapping(value={"/get", "/modify"})
	public void get(@RequestParam("bno") Long bno, Model model, Criteria criteria) {
		log.info("> BoardController.get() ...");
		model.addAttribute("boardVO", this.boardService.get(bno));
		model.addAttribute("criteria", criteria);
	}
*/
		
/*
 	// [1]
	// <a href="/board/get/${ board.bno }"></a>
	@GetMapping("/get/{bno}")
	public String get(@PathVariable("bno") Long bno, Model model ) {
		log.info("> BoardController.get() POST...");
		this.boardService.get(bno);
		return "/board/get";
		
	}
*/
}
