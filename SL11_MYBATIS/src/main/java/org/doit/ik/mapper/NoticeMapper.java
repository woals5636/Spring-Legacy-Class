package org.doit.ik.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.doit.ik.domain.MemberVO;
import org.doit.ik.domain.NoticeVO;

public interface NoticeMapper {
	
	// @Select("SELECT COUNT(*) CNT        FROM NOTICES       WHERE ${ filed } LIKE '%${ param2 }%'")
	public int getCount(@Param("field") String field, @Param("query") String query) throws ClassNotFoundException, SQLException;
	
	public List<NoticeVO> getNotices(int page, String field, String query) throws ClassNotFoundException, SQLException;
	
	public int delete(String seq) throws ClassNotFoundException, SQLException;
	
	public int update(NoticeVO notice) throws ClassNotFoundException, SQLException;
	
	public NoticeVO getNotice(String seq) throws ClassNotFoundException, SQLException;
	
	public int insert(NoticeVO notice) throws ClassNotFoundException, SQLException;

	public void hitUp(String seq) throws ClassNotFoundException, SQLException ;   
	
	public int getHit(String seq) throws ClassNotFoundException, SQLException ;
}
