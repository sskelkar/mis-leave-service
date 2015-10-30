package com.maxxton.mis.leave.repository;

import org.springframework.data.repository.CrudRepository;

import com.maxxton.mis.leave.domain.EmployeeLeave;

public interface EmployeeLeaveRepository extends CrudRepository<EmployeeLeave, Long> {
  EmployeeLeave findByEmployeeIdAndLeaveTypeIdAndYear(Long employeeId, Long leaveTypeId, Long year);
}
