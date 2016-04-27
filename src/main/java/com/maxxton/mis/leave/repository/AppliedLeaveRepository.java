package com.maxxton.mis.leave.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maxxton.mis.leave.domain.AppliedLeave;

public interface AppliedLeaveRepository extends JpaRepository<AppliedLeave, Long> {
  List<AppliedLeave> findByEmployeeId(Long employeeId);

//  List<AppliedLeave> findByEmployeeIdAndLeaveStatusId(Long employeeId, Long leaveStatusId);
//
//  AppliedLeave findByAppliedLeaveId(Long appliedLeaveId);
}
