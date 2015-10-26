package com.maxxton.mis.leave.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EmployeeLeave
{
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Number employeeLeaveId;
  private Number employeeId;
  private Number year;
  private Number leaveCount;
  private Number leaveTypeId;

  public Number getEmployeeLeaveId()
  {
    return employeeLeaveId;
  }
  public void setEmployeeLeaveId(Number employeeLeaveId)
  {
    this.employeeLeaveId = employeeLeaveId;
  }
  public Number getEmployeeId()
  {
    return employeeId;
  }
  public void setEmployeeId(Number employeeId)
  {
    this.employeeId = employeeId;
  }
  public Number getYear()
  {
    return year;
  }
  public void setYear(Number year)
  {
    this.year = year;
  }
  public Number getLeaveCount()
  {
    return leaveCount;
  }
  public void setLeaveCount(Number leaveCount)
  {
    this.leaveCount = leaveCount;
  }
  public Number getLeaveTypeId()
  {
    return leaveTypeId;
  }
  public void setLeaveTypeId(Number leaveTypeId)
  {
    this.leaveTypeId = leaveTypeId;
  }
}
