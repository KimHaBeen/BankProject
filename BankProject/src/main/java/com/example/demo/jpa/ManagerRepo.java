package com.example.demo.jpa;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.vo.ManagerVO;

public interface ManagerRepo extends JpaRepository<ManagerVO, String>{
	@Query(value = "select manager_id,manager_pw from manager", nativeQuery = true)
	public List<Map<String,String>> selectManagerIdPw();
	
	@Query(value = "select e.employee_name from employee e join manager m on e.employee_num = m.employee_num"
			+ " where m.manager_id = :id")
	public String selectName(@Param(value="id") String id);

}
