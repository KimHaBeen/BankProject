package com.example.demo.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name="plus")
public class PlusVO {
	
	@Id
	private int num;
	private String exchange_sign;
	private String account_num;
	private String plus_text;
	private int exchange_money;
	private String exchage_date;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getExchange_sign() {
		return exchange_sign;
	}
	public void setExchange_sign(String exchange_sign) {
		this.exchange_sign = exchange_sign;
	}
	public String getAccount_num() {
		return account_num;
	}
	public void setAccount_num(String account_num) {
		this.account_num = account_num;
	}
	public String getPlus_text() {
		return plus_text;
	}
	public void setPlus_text(String plus_text) {
		this.plus_text = plus_text;
	}
	public int getExchange_money() {
		return exchange_money;
	}
	public void setExchange_money(int exchange_money) {
		this.exchange_money = exchange_money;
	}
	public String getExchage_date() {
		return exchage_date;
	}
	public void setExchage_date(String exchage_date) {
		this.exchage_date = exchage_date;
	}
	@Override
	public String toString() {
		return "PlusVO [num=" + num + ", exchange_sign=" + exchange_sign + ", account_num=" + account_num
				+ ", plus_text=" + plus_text + ", exchange_money=" + exchange_money + ", exchage_date=" + exchage_date
				+ "]";
	}
	
	
}
