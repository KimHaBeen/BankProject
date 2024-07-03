package com.example.demo.jpa;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.vo.MemberVO;

public interface MemberRepo extends JpaRepository<MemberVO, String>{
	@Query(value = "select id,pw from member", nativeQuery = true)
	public List<Map<String,String>> selectMemberIdPw();
	
	@Query(value = "select id from member", nativeQuery = true)
	public List<String> selectID();
	
	@Query(value = "select id from member", nativeQuery = true)
	public List<String> selectMemberId();
	
	@Query(value = "select * from member where id = :id and pw = :pw", nativeQuery = true)
	public MemberVO loginIdPw(@Param (value="id") String id, @Param (value="pw") String pw);
	
	@Query(value = "select count(*) from member where id = :id", nativeQuery = true)
	public String idCK(@Param (value="id") String id);
	
	@Query(value = "select count(*) from member where id = :id and pw = :pw", nativeQuery = true)
	public String pw_ck(@Param (value="id") String id, @Param (value="pw") String pw);
	
	@Query(value = "delete from member where id = :id and pw = :pw ", nativeQuery = true)
	public String delete_member(@Param (value="id") String id, @Param (value="pw") String pw);
}
