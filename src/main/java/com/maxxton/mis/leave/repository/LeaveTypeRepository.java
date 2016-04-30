package com.maxxton.mis.leave.repository;

import java.io.Serializable;

import org.hibernate.jpa.criteria.CriteriaUpdateImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import com.maxxton.mis.leave.domain.enumeration.LeaveType;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Serializable> {
  
  LeaveType findByNameIgnoreCase(String name);
}
