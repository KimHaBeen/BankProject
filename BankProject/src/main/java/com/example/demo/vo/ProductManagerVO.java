package com.example.demo.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity(name="product_manager")
public class ProductManagerVO {
	@Id
	private String product_num;
	private String deposit_name;
	private String create_date;
	private int min_money;
	private String rate;
	
	public String getProduct_num() {
		return product_num;
	}
	public void setProduct_num(String product_num) {
		this.product_num = product_num;
	}
	public String getDeposit_name() {
		return deposit_name;
	}
	public void setDeposit_name(String deposit_name) {
		this.deposit_name = deposit_name;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public int getMin_money() {
		return min_money;
	}
	public void setMin_money(int min_money) {
		this.min_money = min_money;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	
	@Override
	public String toString() {
		return "ProductManagerVO [product_num=" + product_num + ", deposit_name=" + deposit_name + ", create_date="
				+ create_date + ", min_money=" + min_money + ", rate=" + rate + "]";
	}
}
