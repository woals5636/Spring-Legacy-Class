package org.doit.ik.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Param;
import org.doit.ik.domain.MemberVO;

public interface MemberMapper {
 
	// 회원 정보 O + 권한 정보 X
   public MemberVO getMember(String id) throws ClassNotFoundException, SQLException;
   
   public int insert(MemberVO member) throws ClassNotFoundException, SQLException;
   
   // 회원 정보 O + 권한 정보 O
   public MemberVO read(@Param("userid")String userid)throws ClassNotFoundException, SQLException;
   
}