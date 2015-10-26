package com.maxxton.mis.leave.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LeaveType
{
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long leaveTypeId; 
  private String name;
  
  public Long getLeaveTypeId()
  {
    return leaveTypeId;
  }
  public void setLeaveTypeId(Long leaveTypeId)
  {
    this.leaveTypeId = leaveTypeId;
  }
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
}
