package com.maxxton.mis.leave.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.maxxton.mis.leave.domain.AppliedLeave;
import com.maxxton.mis.leave.domain.EmployeeLeave;

public interface AppliedLeaveRepository extends JpaRepository<AppliedLeave, Long>, JpaSpecificationExecutor<AppliedLeave> {
  List<AppliedLeave> findByEmployeeId(Long employeeId);

  List<AppliedLeave> findByEmployeeIdAndApplicationDateLessThanEqualAndLeaveStatusLeaveStatusIdNot(Long employeeId, Date appliedDate, Long leaveStatusId);
//  List<AppliedLeave> findByEmployeeIdAndLeaveStatusId(Long employeeId, Long leaveStatusId);
//
//  AppliedLeave findByAppliedLeaveId(Long appliedLeaveId);
}
