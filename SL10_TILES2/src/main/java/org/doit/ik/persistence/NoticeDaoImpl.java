package org.doit.ik.persistence;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.doit.ik.domain.NoticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import lombok.AllArgsConstructor;
import oracle.net.aso.i;

@Repository
@AllArgsConstructor
// @Transactional
public class NoticeDaoImpl implements NoticeDao{

	private NamedParameterJdbcTemplate npJdbcTemplate;

	// private DataSourceTransactionManager transactionManager;

	// private TransactionTemplate transactionTemplate;

	@Override
	public int getCount(String field, String query) throws ClassNotFoundException, SQLException
	{
		String sql = " SELECT COUNT(*) "
				+ " CNT FROM NOTICES "
				+ " WHERE " + field + " LIKE :query";

		MapSqlParameterSource paramSource = new MapSqlParameterSource();

		paramSource.addValue("query", query);

		return this.npJdbcTemplate.queryForObject(sql, paramSource, Integer.class);
	}

	@Override
	public List<NoticeVO> getNotices(int page, String field, String query) throws ClassNotFoundException, SQLException
	{					

		int srow = 1 + (page-1)*15; // 1, 16, 31, 46, 61, ... an = a1 + (n-1)*d
		int erow = 15 + (page-1)*15; //15, 30, 45, 60, 75, ...

		String sql = " SELECT * FROM "
				+ " (SELECT ROWNUM NUM, N.* "
				+ " FROM (SELECT * FROM NOTICES "
				+ " WHERE "+field+" LIKE :query "
				+ " ORDER BY REGDATE DESC) N)"
				+ " WHERE NUM BETWEEN :srow AND :erow ";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("query", "%" + query + "%");
		paramMap.put("srow", srow);
		paramMap.put("erow", erow);

		return this.npJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper <NoticeVO> (NoticeVO.class));

	}

	@Override
	public int delete(String seq) throws ClassNotFoundException, SQLException
	{
		String sql = " DELETE FROM notices "
				+ " WHERE SEQ = :seq ";

		MapSqlParameterSource paramSource = new MapSqlParameterSource();

		paramSource.addValue("seq", seq);

		return this.npJdbcTemplate.update(sql, paramSource);
	}

	@Override
	public int update(NoticeVO notice) throws ClassNotFoundException, SQLException{

		// 2. 데이터 베이스를 연동하기 위한 쿼리와 데이터베이스 연동을 위한 코드를 작성
		String sql = " UPDATE notices "
				+ " SET title = :title , content = :content , filesrc = :filesrc "
				+ " WHERE SEQ = :seq ";
		/* 
	      MapSqlParameterSource parameterSource = new MapSqlParameterSource();
	      parameterSource.addValue("title", notice.getTitle());
	      parameterSource.addValue("content", notice.getContent());
	      parameterSource.addValue("filesrc", notice.getFilesrc());
	      parameterSource.addValue("seq", notice.getSeq());      

	      return this.npJdbcTemplate.update(sql,  parameterSource );
		 */

		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(notice);

		return this.npJdbcTemplate.update(sql, paramSource);
	}

	@Override
	public NoticeVO getNotice(String seq) throws ClassNotFoundException, SQLException
	{
		String sql = " SELECT * "
				+ " FROM notices "
				+ " WHERE SEQ = :seq ";

		MapSqlParameterSource paramSource = new MapSqlParameterSource();

		paramSource.addValue("seq", seq);

		return this.npJdbcTemplate.queryForObject(sql, paramSource, new BeanPropertyRowMapper <NoticeVO> (NoticeVO.class));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int insert(NoticeVO notice) throws ClassNotFoundException, SQLException {
		// 1. 공지사항 작성
		String sql = "INSERT INTO notices"
				+ " (SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC)"
				+ " VALUES( "
				+ "(SELECT NVL(MAX(TO_NUMBER(SEQ)),0)+1 FROM notices), :title, :content, :writer , SYSDATE, 0, :filesrc)";

		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(notice);
		this.npJdbcTemplate.update(sql, paramSource);

		// 2. 포인트 증가
		sql = " UPDATE MEMBER "
				+ " SET point = point + 1 "
				+ " WHERE ID = :id ";

		MapSqlParameterSource mParamSource = new MapSqlParameterSource();
		mParamSource.addValue("id", "jam");
		int rowCount = this.npJdbcTemplate.update(sql, mParamSource);

		return rowCount; 
	}

	/*
	// [1] 트랜잭션 처리되지 않은 메서드
	@Override
	public void insertAndPointUpOfMember(NoticeVO notice, String id) throws ClassNotFoundException, SQLException {
		// 1. 공지사항 작성
		String sql = "INSERT INTO notices"
				+ " (SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC)"
				+ " VALUES( "
				+ "(SELECT NVL(MAX(TO_NUMBER(SEQ)),0)+1 FROM notices), :title, :content, :writer , SYSDATE, 0, :filesrc)";

		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(notice);
		this.npJdbcTemplate.update(sql, paramSource);

		// 2. 포인트 증가
		sql = " UPDATE MEMBER "
			+ " SET point = point + 1 "
			+ " WHERE ID = :id ";

		MapSqlParameterSource mParamSource = new MapSqlParameterSource();
		mParamSource.addValue("id", id);
		this.npJdbcTemplate.update(sql, mParamSource);
	}
	 */
	/*
	// [2] transactionManager를 사용해서 트랜잭션 처리
	@Override
	public void insertAndPointUpOfMember(NoticeVO notice, String id) throws ClassNotFoundException, SQLException {
		// 1. 공지사항 작성
		String sql = "INSERT INTO notices"
				+ " (SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC)"
				+ " VALUES( "
				+ "(SELECT NVL(MAX(TO_NUMBER(SEQ)),0)+1 FROM notices), :title, :content, :writer , SYSDATE, 0, :filesrc)";

		// 2. 포인트 증가
		String sql2 = " UPDATE MEMBER "
			+ " SET point = point + 1 "
			+ " WHERE ID = :id ";

		TransactionDefinition definition = new DefaultTransactionDefinition();
		TransactionStatus status = this.transactionManager.getTransaction(definition );
		try {
			// 1
			SqlParameterSource paramSource = new BeanPropertySqlParameterSource(notice);
			this.npJdbcTemplate.update(sql, paramSource);
			// 2
			MapSqlParameterSource mParamSource = new MapSqlParameterSource();
			mParamSource.addValue("id", id);
			this.npJdbcTemplate.update(sql2, mParamSource);

			this.transactionManager.commit(status);

		} catch (Exception e) {

			this.transactionManager.rollback(status);

		}

	}
	 */
	/*
	// [3] transactionTemplate를 사용해서 트랜잭션 처리
	@Override
	public void insertAndPointUpOfMember(NoticeVO notice, String id) throws ClassNotFoundException, SQLException {
		// 1. 공지사항 작성
		String sql = "INSERT INTO notices"
				+ " (SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC)"
				+ " VALUES( "
				+ "(SELECT NVL(MAX(TO_NUMBER(SEQ)),0)+1 FROM notices), :title, :content, :writer , SYSDATE, 0, :filesrc)";

		// 2. 포인트 증가
		String sql2 = " UPDATE MEMBER "
			+ " SET point = point + 1 "
			+ " WHERE ID = :id ";

		// p515 예제
		this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				// 1
				SqlParameterSource paramSource = new BeanPropertySqlParameterSource(notice);
				npJdbcTemplate.update(sql, paramSource);
				// 2
				MapSqlParameterSource mParamSource = new MapSqlParameterSource();
				mParamSource.addValue("id", id);
				npJdbcTemplate.update(sql2, mParamSource);
			}
		});

	}
	 */
	/*	// [5]
	@Override
	// @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insertAndPointUpOfMember(NoticeVO notice, String id) throws ClassNotFoundException, SQLException {
		insert(notice);

		notice.setTitle( notice.getTitle() + " : two ");
		insert(notice);
	}
	 */

	@Override
	@Transactional(isolation = Isolation.DEFAULT) // DBMS(오라클)에서의 지원 수준으로 격리
	public void hitUp(String seq) throws ClassNotFoundException, SQLException {
		String sql = "UPDATE notices "
				+ " SET hit = hit + 1 "
				+ " WHERE seq = :seq ";
		MapSqlParameterSource paramSource =new MapSqlParameterSource();
		paramSource.addValue("seq", seq);       
		this.npJdbcTemplate.update(sql, paramSource);
	}

	@Override
	@Transactional
	public int getHit(String seq) throws ClassNotFoundException, SQLException {
		String sql = "SELECT hit  "
				+ " FROM notices "
				+ " WHERE seq = :seq";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("seq", seq);
		return this.npJdbcTemplate.queryForObject(sql, paramMap, Integer.class);      
	}
}// class