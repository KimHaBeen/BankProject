package com.example.demo.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name="manager")
public class ManagerVO {
	
	@Id
	private String manager_id;
	private String manager_pw;
	private int employee_num;
	
	public String getManager_id() {
		return manager_id;
	}
	public void setManager_id(String manager_id) {
		this.manager_id = manager_id;
	}
	public String getManager_pw() {
		return manager_pw;
	}
	public void setManager_pw(String manager_pw) {
		this.manager_pw = manager_pw;
	}
	public int getEmployee_num() {
		return employee_num;
	}
	public void setEmployee_num(int employee_num) {
		this.employee_num = employee_num;
	}
	@Override
	public String toString() {
		return "ManagerVO [manager_id=" + manager_id + ", manager_pw=" + manager_pw + ", employee_num=" + employee_num
				+ "]";
	}
}
