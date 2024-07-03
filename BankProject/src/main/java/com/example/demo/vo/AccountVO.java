package com.example.demo.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name="account")
public class AccountVO {
	
	@Id
	private String account_num;
	private String id;
	private String product_num;
	private int account_pw;
	private String reg_date;
	private int total;
	private int dormant_date;
	
	public String getAccount_num() {
		return account_num;
	}
	public void setAccount_num(String account_num) {
		this.account_num = account_num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProduct_num() {
		return product_num;
	}
	public void setProduct_num(String product_num) {
		this.product_num = product_num;
	}
	public int getAccount_pw() {
		return account_pw;
	}
	public void setAccount_pw(int account_pw) {
		this.account_pw = account_pw;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getDormant_date() {
		return dormant_date;
	}
	public void setDormant_date(int dormant_date) {
		this.dormant_date = dormant_date;
	}
	
	@Override
	public String toString() {
		return "AccountVO [account_num=" + account_num + ", id=" + id + ", product_num=" + product_num + ", account_pw="
				+ account_pw + ", reg_date=" + reg_date + ", total=" + total + ", dormant_date=" + dormant_date
				+ ", product_money=" +"]";
	}
	
}
