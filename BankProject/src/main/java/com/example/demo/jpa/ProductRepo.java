package com.example.demo.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.vo.ProductManagerVO;

import jakarta.validation.constraints.Pattern;

public interface ProductRepo extends JpaRepository<ProductManagerVO, String>{
	@Query(value="select * from product_manager where product_num like '#1%'", nativeQuery = true)
	public List<ProductManagerVO> showMain();
	@Query(value="select * from product_manager where product_num like '#2%'", nativeQuery = true)
	public List<ProductManagerVO> showDeposit();
	@Query(value="select * from product_manager where product_num like '#3%'", nativeQuery = true)
	public List<ProductManagerVO> showSavings();

	@Query(value="select * from product_manager where product_num = :product_num", nativeQuery = true)
	public ProductManagerVO showProduct(@Param(value = "product_num")String product_num);

	@Query(value = "select product_num from product_manager", nativeQuery = true)
	public List<String> product_num();
	@Query(value = "select count(*) from product_manager where product_num = :product_num", nativeQuery = true)
	public int productNum_CK(@Param("product_num") String product_num);
	
	@Query(value="select count(*) from account where id = :id and account_num like '4%'", nativeQuery = true)
	public int account_ckeck(@Param("id")String id);
	
	@Query(value="SELECT product_num FROM product_manager WHERE PRODUCT_NUM LIKE '#1%' ORDER BY TO_NUMBER(REGEXP_SUBSTR(PRODUCT_NUM, '\\d+$')) DESC "
			+ "FETCH FIRST 1 ROW ONLY", nativeQuery = true)
	public String main_ck();
	
	@Query(value="SELECT product_num FROM product_manager WHERE PRODUCT_NUM LIKE '#2%' ORDER BY TO_NUMBER(REGEXP_SUBSTR(PRODUCT_NUM, '\\d+$')) DESC "
			+ "FETCH FIRST 1 ROW ONLY", nativeQuery = true)
	public String deposit_ck();
	
	@Query(value="SELECT product_num FROM product_manager WHERE PRODUCT_NUM LIKE '#3%' ORDER BY TO_NUMBER(REGEXP_SUBSTR(PRODUCT_NUM, '\\d+$')) DESC "
			+ "FETCH FIRST 1 ROW ONLY", nativeQuery = true)
	public String savings_ck();
}
