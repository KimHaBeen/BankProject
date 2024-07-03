package com.example.demo.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.vo.EmployeeVO;

public interface EmployeeRepo extends JpaRepository<EmployeeVO, Integer>{
	@Query(value = "select employee_num from employee", nativeQuery = true)
	public List<Integer> selectEnployeeNum();

}
