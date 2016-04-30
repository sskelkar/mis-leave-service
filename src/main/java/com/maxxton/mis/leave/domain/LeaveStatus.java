package com.maxxton.mis.leave.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LeaveStatus {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long leaveStatusId;
  private String name;

  public Long getLeaveStatusId() {
    return leaveStatusId;
  }

  public void setLeaveStatusId(Long leaveStatusId) {
    this.leaveStatusId = leaveStatusId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}