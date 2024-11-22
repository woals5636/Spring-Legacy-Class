package org.doit.ik.mapper;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper {
	
	String getTime();
	
	
	// Mapper.xml
	@Select("SELECT SYSDATE+1 FROM dual")
	String getNextTime();
}
