package com.maxxton.mis.leave.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maxxton.mis.leave.domain.enumeration.LeaveStatus;

public interface LeaveStatusRepository extends JpaRepository<LeaveStatus, Long> {

  LeaveStatus findByName(String name);
}
