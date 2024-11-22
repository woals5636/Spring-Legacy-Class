package org.doit.ik;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.doit.ik.domain.NoticeVO;
import org.doit.ik.persistence.NoticeDaoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
class NoticeDaoTest {
	   private NamedParameterJdbcTemplate npJdbcTemplate;

	   private DataSourceTransactionManager transactionManager;
		
	   private TransactionTemplate transactionTemplate;
	   
	@Test
	void testNoticeInsert() {
		// System.out.println("Hello World");
		NoticeDaoImpl noticeDaoImpl = new NoticeDaoImpl(npJdbcTemplate, transactionTemplate);
		NoticeVO notice = new NoticeVO();
		notice.setTitle("첫번째 게시글");
		notice.setContent("첫번째 게시글");
		notice.setFilesrc("첫번째 게시글");
		
		int rowCount = 0;
		try {
			rowCount = noticeDaoImpl.insert(notice);
			System.out.println(rowCount);
		} catch (ClassNotFoundException|SQLException e) {
			e.printStackTrace();
		}
		System.out.println("공지사항 추가 ....");
	}

}
