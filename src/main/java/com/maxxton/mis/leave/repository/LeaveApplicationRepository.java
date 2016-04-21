package com.maxxton.mis.leave.repository;

import org.springframework.data.repository.CrudRepository;

import com.maxxton.mis.leave.domain.AppliedLeave;

public interface LeaveApplicationRepository extends CrudRepository<AppliedLeave, Long> {
  Iterable<AppliedLeave> findByEmployeeId(Long employeeId);

  Iterable<AppliedLeave> findByEmployeeIdAndLeaveStatusId(Long employeeId, Long leaveStatusId);

  AppliedLeave findByAppliedLeaveId(Long appliedLeaveId);
}
