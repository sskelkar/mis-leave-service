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
  private Number employeeCompOffId;
  private Number employeeLeaveId;
  private Number managerId;
  private String commentByManager;
  private Date startDate;
  private Date endDate;

  public Number getEmployeeCompOffId()
  {
    return employeeCompOffId;
  }
  public void setEmployeeCompOffId(Number employeeCompOffId)
  {
    this.employeeCompOffId = employeeCompOffId;
  }
  public Number getEmployeeLeaveId()
  {
    return employeeLeaveId;
  }
  public void setEmployeeLeaveId(Number employeeLeaveId)
  {
    this.employeeLeaveId = employeeLeaveId;
  }
  public Number getManagerId()
  {
    return managerId;
  }
  public void setManagerId(Number managerId)
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
