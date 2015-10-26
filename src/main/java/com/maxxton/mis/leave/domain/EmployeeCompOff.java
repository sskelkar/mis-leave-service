package com.maxxton.mis.leave.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EmployeeCompOff
{
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long employeeCompOffId;
  private Long employeeLeaveId;
  private Long managerId;
  private String commentByManager;
  private Date startDate;
  private Date endDate;

  public Long getEmployeeCompOffId()
  {
    return employeeCompOffId;
  }
  public void setEmployeeCompOffId(Long employeeCompOffId)
  {
    this.employeeCompOffId = employeeCompOffId;
  }
  public Long getEmployeeLeaveId()
  {
    return employeeLeaveId;
  }
  public void setEmployeeLeaveId(Long employeeLeaveId)
  {
    this.employeeLeaveId = employeeLeaveId;
  }
  public Long getManagerId()
  {
    return managerId;
  }
  public void setManagerId(Long managerId)
  {
    this.managerId = managerId;
  }
  public String getCommentByManager()
  {
    return commentByManager;
  }
  public void setCommentByManager(String commentByManager)
  {
    this.commentByManager = commentByManager;
  }
  public Date getStartDate()
  {
    return startDate;
  }
  public void setStartDate(Date startDate)
  {
    this.startDate = startDate;
  }
  public Date getEndDate()
  {
    return endDate;
  }
  public void setEndDate(Date endDate)
  {
    this.endDate = endDate;
  }
  
}
