package org.doit.ik.service;

import java.sql.SQLException;

import org.doit.ik.domain.NoticeVO;

public interface MemberShipService {
	
	// 트랜잭션 처리를 위한 메서드 추가
	// 새글작성 + 포인트 1증가
	public void insertAndPointUpOfMember(NoticeVO notice, String id) throws Exception;
}
