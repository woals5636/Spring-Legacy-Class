package org.doit.ik;

import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.doit.ik.domain.DeptDTO;
import org.doit.ik.domain.EmpDTO;
import org.doit.ik.mapper.TimeMapper;
import org.doit.ik.mapper.scott.DeptMapper;
import org.doit.ik.mapper.scott.EmpMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.Setter;

@Controller
public class ScottController {

	private static final Logger logger = LoggerFactory.getLogger(ScottController.class);

	// DI
	//@Autowired
	@Setter(onMethod=@__({@Autowired}))
	private DeptMapper deptMapper;
	
	@Setter(onMethod=@__({@Autowired}))
	private EmpMapper empMapper;

	// @RequestMapping(value = "/scott/dept", method = RequestMethod.GET)
	@GetMapping(value = "/scott/dept")	
	public String dept(Locale locale, Model model) {
		logger.info("> ScottController.dept()...");

		ArrayList<DeptDTO> list = this.deptMapper.selectDept();
		model.addAttribute("list", list);

		//        /WEB-INF/views/   dept  .jsp
		return "/scott/dept";
	}
	
	@PostMapping(value = "/scott/emp")
	public String emp(Locale locale, Model model
			, @RequestParam(value = "deptno")  int [] deptnos ) {
		logger.info("> ScottController.emp()...");
 
		ArrayList<EmpDTO> list = this.empMapper.selectEmp(deptnos);
		model.addAttribute("list", list);
		
		return "/scott/emp";
	}
	/* [1] 예전 풀이
	// ?deptno=10&deptno=20&deptno=30 
	// @RequestMapping(value = "/scott/emp", method = RequestMethod.POST)
	@PostMapping(value = "/scott/emp")
	public String emp(Locale locale, Model model
			, HttpServletRequest request) {
		logger.info("> ScottController.emp()...");
 
		int [] deptnos = null;
		String [] pdeptnos = request.getParameterValues("deptno");
		deptnos = new int[  pdeptnos.length ];
		for (int i = 0; i < pdeptnos.length; i++) {
			deptnos[i] = Integer.parseInt( pdeptnos[i] );
		}

		return "/scott/emp";
	}
	*/

}
