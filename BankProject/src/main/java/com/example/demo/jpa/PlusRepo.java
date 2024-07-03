package com.example.demo.jpa;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.vo.PlusVO;

import jakarta.transaction.Transactional;

public interface PlusRepo extends JpaRepository<PlusVO, Integer>{
	@Query(value="insert into plus(num, account_num, exchange_money) values(plus_seq.nextval, :account_num, :exchange_money)", nativeQuery = true)
	public int insertPlus(@Param("account_num") String account_num,@Param("exchange_money") int exchange_money);
	
	@Query(value="update account a set total = total + :exchange_money where account_num=:remit_account", nativeQuery = true)
	public void updatePlus(@Param("exchange_money")int exchange_money, @Param("remit_account") String remit_account);

	@Query(value = "insert into plus (num, account_num, plus_text, exchange_money) values(plus_seq.nextval, :account_num, :plus_text, :exchange_money)", nativeQuery = true)
	public void insertRemitPlus(@Param("account_num") String account_num, @Param("plus_text") String plus_text, @Param("exchange_money") int exchange_money);
	
	@Query(value="insert into plus values(plus_seq.nextval, '+', :account_num, :deposit_name, :exchange_money, sysdate)", nativeQuery = true)
	public int insertProduct(@Param("account_num") String account_numt,
							@Param("deposit_name") String deposit_name, @Param("exchange_money") int exchange_money);

}
