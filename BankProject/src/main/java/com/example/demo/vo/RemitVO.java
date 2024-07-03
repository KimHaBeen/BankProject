package com.example.demo.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name="remit")
public class RemitVO {
	
	@Id
	private int num;
	private String exchange_sign;
	private String account_num;
	private String remit_account;
	private String remit_text;
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
	public String getRemit_account() {
		return remit_account;
	}
	public void setRemit_account(String remit_account) {
		this.remit_account = remit_account;
	}
	public String getRemit_text() {
		return remit_text;
	}
	public void setRemit_text(String remit_text) {
		this.remit_text = remit_text;
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
		return "RemitVO [num=" + num + ", exchange_sign=" + exchange_sign + ", account_num=" + account_num
				+ ", remit_account=" + remit_account + ", remit_text=" + remit_text + ", exchange_money="
				+ exchange_money + ", exchage_date=" + exchage_date + "]";
	}
	
	

}
