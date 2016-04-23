package com.maxxton.mis.leave.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maxxton.mis.leave.domain.EmployeeLeave;

public interface EmployeeLeaveRepository extends JpaRepository<EmployeeLeave, Long> {

  List<EmployeeLeave> findByEmployeeIdAndYearGreaterThanEqual(Long employeeId, Long year);
  
  
//  EmployeeLeave findByEmployeeIdAndLeaveTypeIdAndYear(Long employeeId, Long leaveTypeId, Long year);
}
