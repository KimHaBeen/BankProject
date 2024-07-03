package com.example.demo.jpa;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.vo.AccountVO;

public interface AccountRepo extends JpaRepository<AccountVO, String>{
	@Query(value="insert into account(account_num, id, product_num, account_pw, total) values(:account_num, :id, :product_num, :account_pw, :total)", nativeQuery = true)
	public void insertAccount(@Param(value="account_num") String account_num, @Param(value="id") String id, @Param(value="product_num") String product_num,
							  @Param(value="account_pw") int account_pw, @Param(value="total") int total);
	
	@Query(value="select account_num from account where id=:na", nativeQuery = true)
	public List<String> selectAccount(@Param(value="na") String id);
	
	@Query(value = "select account_num, total from account where id = :na", nativeQuery = true)
	public List<Map<String,Integer>> selectAccount2(@Param(value="na") String id);
	
	@Query(value="select account_num, total from account where id = :na", nativeQuery = true)
	public List<String> selectAll(@Param(value="na") String id);
	
	@Query(value="select total from account where account_num=:account_num", nativeQuery = true)
	public String selectTotal(@Param(value="account_num") String account);
//	UPDATE account SET product_num = '#201' WHERE account_num = '41212';
	@Query(value="update account set product_num = :product_num, total = total - :money where account_num = :account_num", nativeQuery = true)
	public void updateAccount(@Param(value="product_num") String product_num,@Param(value="money") int money ,@Param(value="account_num") String account_num);
	
	@Query(value="select count(*) from account where account_num=:account_num", nativeQuery = true)
	public String selectAccountRemit(@Param(value="account_num") String account);
	
	@Query(value ="select account_num from account where account_num=:account_num", nativeQuery = true)
	public String selectAccountNum(@Param(value="account_num") String account);
	
	@Query(value="select * from account where id = :id", nativeQuery = true)
	public List<AccountVO> selectAllById(@Param(value="id") String id);
	
	@Query(value="select account_pw from account where id = :id", nativeQuery = true)
	public List<Integer> selectAccountPW(@Param(value="id") String id);
	//계좌 소유주 검색
	@Query(value = "select m.name from member m join account a on m.id = a.id where a.account_num = :account_num", nativeQuery = true)
	public String account_name(@Param(value="account_num") String account_num);
	
	@Query(value = "update account set total = total + :addMoney where account_num = :account_num", nativeQuery = true)
	public void updateAccountMoney(@Param(value = "account_num")String account_num, @Param(value= "addMoney")int addMoney);
	
	@Query(value = "update account set total = total - :subMoney where account_num = :account_num", nativeQuery = true)
	public void updateAccountMoney2(@Param(value = "account_num")String account_num, @Param(value= "subMoney")int subMoney);
	
	@Query(value="select account_pw from account where account_num = :account_num", nativeQuery = true)
	public int selectAccountPWByAccountNum(@Param(value="account_num") String account_num);

	@Query(value="select count(*) from account where id = :id", nativeQuery = true)
	public String delete_ck(@Param(value="id") String id);

	public AccountVO getById(String account_num);
	
}
