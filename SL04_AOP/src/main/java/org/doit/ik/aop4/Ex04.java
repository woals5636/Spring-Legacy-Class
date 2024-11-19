package org.doit.ik.aop4;

import org.doit.ik.aop.Calculator;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Ex04 {

   public static void main(String[] args) {
      // p209 XML 스키마 기반 AOP 처리
      
      GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:org/doit/ik/aop4/application-context4.xml");
      
      Calculator calc = ctx.getBean("calc4", Calculator.class);

      System.out.println( calc.add(3, 5));
      
      System.out.println( "END");
      
   } // main

} // class
