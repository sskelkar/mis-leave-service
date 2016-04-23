package com.maxxton.mis.leave.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maxxton.mis.leave.domain.LeaveStatus;

public interface LeaveStatusRepository extends JpaRepository<LeaveStatus, Long> {

  LeaveStatus findByName(String name);
}
