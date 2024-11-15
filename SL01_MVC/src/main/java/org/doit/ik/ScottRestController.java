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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Setter;

// Ajax 요청을 처리하는 컨트롤러
@RestController
public class ScottRestController {

	private static final Logger logger = LoggerFactory.getLogger(ScottRestController.class);

	@Setter(onMethod=@__({@Autowired}))
	private DeptMapper deptMapper;

	// json -> DeptDTO  == @RequestBody
	@PostMapping("/scott/dept/new")
	public ResponseEntity<String> insertDept(@RequestBody DeptDTO dto){
		logger.info("> ScottRestController.insertDept()...");
		int rowCount = this.deptMapper.insertDept(dto);
		return rowCount==1
				? new ResponseEntity<>("SUCCESS", HttpStatus.OK)
						: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR)					;
	}
	
/*
	//	/scott/dept/delete?deptno=50
	@GetMapping("/scott/dept/delete")
	// public ResponseEntity<String> deleteDept(HttpServletRequest request){
	public ResponseEntity<String> deleteDept(@RequestParam("deptno") int deptno){

		logger.info("> ScottRestController.deleteDept()...");

		// int deptno = Integer.parseInt(request.getParameter("deptno"));

		int rowCount = this.deptMapper.deleteDept(deptno);
		return rowCount==1
				? new ResponseEntity<>("SUCCESS",HttpStatus.OK)
						: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR)
						;
	}
*/

	//	DELETE + /scott/dept/50
	@DeleteMapping("/scott/dept/{deptno}")
	// public ResponseEntity<String> deleteDept(HttpServletRequest request){
	public ResponseEntity<String> deleteDept(@PathVariable("deptno") int deptno){

		logger.info("> ScottRestController.deleteDept()...");

		int rowCount = this.deptMapper.deleteDept(deptno);
		return rowCount==1
				? new ResponseEntity<>("SUCCESS",HttpStatus.OK)
						: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR)
						;
	}


}
