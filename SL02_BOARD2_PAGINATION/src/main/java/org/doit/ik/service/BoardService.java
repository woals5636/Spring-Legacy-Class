package org.doit.ik.service;

import java.util.List;

import org.doit.ik.domain.BoardVO;
import org.doit.ik.domain.Criteria;

public interface BoardService {

	List<BoardVO> getList();	// 페이징 처리 X
	
	List<BoardVO> getListWithPaging( Criteria criteria );	// 페이징 처리 O
	int getTotal( Criteria criteria );	// 총 레코드 수
	
	void register(BoardVO board);
	
	BoardVO get(Long bno);
	
	boolean modify(BoardVO board);
	boolean remove(Long bno);
}
