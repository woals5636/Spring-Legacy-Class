package org.doit.ik.di2;

import org.doit.ik.di.RecordImpl;
import org.doit.ik.di.RecordViewImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// application-context.xml 와 같은 자바 클래스 설정 파일
@Configuration // 클래스를 스프링 설정으로 사용함을 의미
public class Config {

/* 
	<bean id="record" class="org.doit.ik.di.RecordImpl"></bean>
*/
	@Bean // 메서드의 리턴 값을 빈 객체로 사용함을 의미
	public RecordImpl record() {
		return new RecordImpl();
	}

	
/*	
    <bean id="rvi" class="org.doit.ik.di.RecordViewImpl">
    	<property name="record">
    		<ref bean="record"></ref>
    	</property>
    </bean>
*/
	@Bean // (name = "rvi")
	public RecordViewImpl getRecordViewImpl() {
		RecordViewImpl rvi = new RecordViewImpl();
		rvi.setRecord(record());
		return rvi;
	}
}// class
