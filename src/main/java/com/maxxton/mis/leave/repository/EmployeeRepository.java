package com.maxxton.mis.leave.repository;

import org.springframework.data.repository.CrudRepository;

import com.maxxton.mis.leave.domain.Employee;

public interface EmployeeRepository extends  CrudRepository<Employee, Long>
{
  
}
