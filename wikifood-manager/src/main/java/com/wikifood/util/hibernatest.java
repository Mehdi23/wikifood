package com.wikifood.util;

import java.math.BigDecimal;

import com.websystique.spring.configuration.AppConfig;
import com.websystique.spring.model.*;
import com.websystique.spring.service.*;

import org.joda.time.LocalDate;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class hibernatest {
	
	

	public static void main(String[] args) {

		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        EmployeeService service = (EmployeeService) context.getBean("employeeService");
        
        Employee employee1 = new Employee();
		employee1.setName("Han Yenn");
		employee1.setJoiningDate(new LocalDate(2010, 10, 10));
		employee1.setSalary(new BigDecimal(10000));
		employee1.setSsn("ssn00000001");

		/*
		 * Create Employee2
		 */
		Employee employee2 = new Employee();
		employee2.setName("Dan Thomas");
		employee2.setJoiningDate(new LocalDate(2012, 11, 11));
		employee2.setSalary(new BigDecimal(20000));
		employee2.setSsn("ssn00000002");

		/*
		 * Persist both Employees
		 */
		service.saveEmployee(employee1);
		service.saveEmployee(employee2);

	}

}
