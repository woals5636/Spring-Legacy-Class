package org.doit.ik.aop;

public class Ex01 {

	public static void main(String[] args) {
		// P.204 스프링 AOP
		
		Calculator calc = new CalculatorImpl();
		System.out.println(calc.add(4, 2));
		System.out.println(calc.sub(4, 2));
		System.out.println(calc.mult(4, 2));
		System.out.println(calc.div(4, 2));
		
		System.out.println(" END ");
		
	}// main

}// class
