package com.example.demo.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name="employee")
public class EmployeeVO {
	@Id
	private int employee_num;
	private String employee_name;
	private String rrn;
	private String department;
	public int getEmployee_num() {
		return employee_num;
	}
	public void setEmployee_num(int employee_num) {
		this.employee_num = employee_num;
	}
	public String getEmployee_name() {
		return employee_name;
	}
	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}
	public String getRrn() {
		return rrn;
	}
	public void setRrn(String rrn) {
		this.rrn = rrn;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	@Override
	public String toString() {
		return "EmployeeVO [employee_num=" + employee_num + ", employee_name=" + employee_name + ", rrn=" + rrn
				+ ", department=" + department + "]";
	}
	
	
}
