package com.example.demo.jpa;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.vo.RemitVO;

public interface RemitRepo extends JpaRepository<RemitVO, Integer>{
	
	@Query(value="insert into remit values(remit_seq.nextval, '-', :account_num, :remit_account, :remit_text, :exchange_money, sysdate)", nativeQuery = true)
	public int insertRemit(@Param("account_num") String account_num, @Param("remit_account") String remit_account,
							@Param("remit_text") String remit_text, @Param("exchange_money") int exchange_money);
	
	@Query(value="update account a set total = total - :exchange_money where account_num=:account_num", nativeQuery = true)
	public void updateRemit(@Param("exchange_money")int exchange_money, @Param("account_num") String account_num);	
	
	@Query(value = "select account_num,total from account where id = :id", nativeQuery = true)
	public List<Map<String,Integer>> selectNumTotal(@Param(value="id")String id);
	
	@Query(value="insert into remit values(remit_seq.nextval, '-', :account, :account_num, :remit_text, :total, sysdate)", nativeQuery = true)
	public int insertRemit2(@Param("account") String account, @Param("account_num") String account_num,
							@Param("remit_text") String remit_text, @Param("total") int total);
}
