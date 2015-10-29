package com.maxxton.mis.leave.repository;

import org.springframework.data.repository.CrudRepository;

import com.maxxton.mis.leave.domain.LeaveStatus;

public interface LeaveStatusRepository extends CrudRepository<LeaveStatus, Long>
{
  LeaveStatus findByName(String name);
}
