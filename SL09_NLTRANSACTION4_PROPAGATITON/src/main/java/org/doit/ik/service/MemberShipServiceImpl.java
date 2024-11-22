package org.doit.ik.service;

import java.sql.SQLException;

import org.doit.ik.domain.NoticeVO;
import org.doit.ik.persistence.NoticeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberShipServiceImpl implements MemberShipService{

	@Autowired
	private NoticeDao noticeDao;
	
	@Override
	// @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insertAndPointUpOfMember(NoticeVO notice, String id) throws Exception {
		noticeDao.insert(notice);
		
		notice.setTitle( notice.getTitle() + " : two ");
		noticeDao.insert(notice);
	}
	
}
