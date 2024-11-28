package org.doit.ik.mapper;

import java.util.List;

import org.doit.ik.domain.EmpVO;

public interface EmpMapper {
   
   List<EmpVO> selectAll(); // 모든 사원 정보 반환
   EmpVO selectByEmpno(int empno); // 해당 사원 번호 정보 반환
   Integer idCheck(String empno); // id 중복체크 // 타입은 wrapper 클래스로 하는게 확장성에 좋음 ( 예시로 null 값은 int면 에러 Integer면 받을 수 있음)
   
}
