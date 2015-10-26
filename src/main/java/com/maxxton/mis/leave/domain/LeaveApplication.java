package com.maxxton.mis.leave.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LeaveApplication
{
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Number leaveApplicationId; 
  private Number employeeId;
  private Date leaveFrom;
  private Date leaveTo;
  private Number leaveDuration;
  private Number NoOfWorkingDays;
  private Date applicationDate;
  private String commentByApplicant;
  private Number leaveTypeId;
  private Number leaveStatusId;
  private Number isBorrowed;
  
  public Number getLeaveApplicationId()
  {
    return leaveApplicationId;
  }
  public void setLeaveApplicationId(Number leaveApplicationId)
  {
    this.leaveApplicationId = leaveApplicationId;
  }
  public Number getEmployeeId()
  {
    return employeeId;
  }
  public void setEmployeeId(Number employeeId)
  {
    this.employeeId = employeeId;
  }
  public Date getLeaveFrom()
  {
    return leaveFrom;
  }
  public void setLeaveFrom(Date leaveFrom)
  {
    this.leaveFrom = leaveFrom;
  }
  public Date getLeaveTo()
  {
    return leaveTo;
  }
  public void setLeaveTo(Date leaveTo)
  {
    this.leaveTo = leaveTo;
  }
  public Number getLeaveDuration()
  {
    return leaveDuration;
  }
  public void setLeaveDuration(Number leaveDuration)
  {
    this.leaveDuration = leaveDuration;
  }
  public Number getNoOfWorkingDays()
  {
    return NoOfWorkingDays;
  }
  public void setNoOfWorkingDays(Number noOfWorkingDays)
  {
    NoOfWorkingDays = noOfWorkingDays;
  }
  public Date getApplicationDate()
  {
    return applicationDate;
  }
  public void setApplicationDate(Date applicationDate)
  {
    this.applicationDate = applicationDate;
  }
  public String getCommentByApplicant()
  {
    return commentByApplicant;
  }
  public void setCommentByApplicant(String commentByApplicant)
  {
    this.commentByApplicant = commentByApplicant;
  }
  public Number getLeaveTypeId()
  {
    return leaveTypeId;
  }
  public void setLeaveTypeId(Number leaveTypeId)
  {
    this.leaveTypeId = leaveTypeId;
  }
  public Number getLeaveStatusId()
  {
    return leaveStatusId;
  }
  public void setLeaveStatusId(Number leaveStatusId)
  {
    this.leaveStatusId = leaveStatusId;
  }
  public Number getIsBorrowed()
  {
    return isBorrowed;
  }
  public void setIsBorrowed(Number isBorrowed)
  {
    this.isBorrowed = isBorrowed;
  }
}
