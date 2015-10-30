package com.maxxton.mis.leave.repository;

import org.springframework.data.repository.CrudRepository;

import com.maxxton.mis.leave.domain.LeaveApplication;

public interface LeaveApplicationRepository extends CrudRepository<LeaveApplication, Long> {
  Iterable<LeaveApplication> findByEmployeeId(Long employeeId);

  Iterable<LeaveApplication> findByEmployeeIdAndLeaveStatusId(Long employeeId, Long leaveStatusId);

  LeaveApplication findByLeaveApplicationId(Long leaveApplicationId);
}
