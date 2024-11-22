package org.doit.ik.persistence;

import java.sql.SQLException;
import java.util.List;

import org.doit.ik.domain.MemberVO;
import org.doit.ik.domain.NoticeVO;

public interface NoticeDao {
	
	public int getCount(String field, String query) throws ClassNotFoundException, SQLException;
	
	public List<NoticeVO> getNotices(int page, String field, String query) throws ClassNotFoundException, SQLException;
	
	public int delete(String seq) throws ClassNotFoundException, SQLException;
	
	public int update(NoticeVO notice) throws ClassNotFoundException, SQLException;
	
	public NoticeVO getNotice(String seq) throws ClassNotFoundException, SQLException;
	
	public int insert(NoticeVO notice) throws ClassNotFoundException, SQLException;
	
	// 트랜잭션 처리를 위한 메서드 추가
	// 새글작성 + 포인트 1증가
	public void insertAndPointUpOfMember(NoticeVO notice, String id) throws ClassNotFoundException, SQLException;
}
