package com.maxxton.mis.leave.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "leave_application")
public class AppliedLeave {
  @Id
  @Column(name = "leave_application_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LEAVE_APPLICATION")
  @SequenceGenerator(name = "SEQ_LEAVE_APPLICATION", sequenceName = "SEQ_LEAVE_APPLICATION", allocationSize = 1)
  private Long appliedLeaveId;
  private Long employeeId;
  private Date leaveFrom;
  private Date leaveTo;
  private Double leaveDuration;
  private Double noOfWorkingDays;
  private Date applicationDate;
  private String commentByApplicant;
  
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "leave_type_id")
  private LeaveType leaveType;
  
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "leave_status_id")
  private LeaveStatus leaveStatus;
  private Long isBorrowed;
  private Long managerId;
  private String commentByManager;
  private String leaveFromHalf;
  private String leaveToHalf;
  
  public Long getAppliedLeaveId() {
    return appliedLeaveId;
  }

  public void setAppliedLeaveId(Long appliedLeaveId) {
    this.appliedLeaveId = appliedLeaveId;
  }

  public Long getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(Long employeeId) {
    this.employeeId = employeeId;
  }

  public Date getLeaveFrom() {
    return leaveFrom;
  }

  public void setLeaveFrom(Date leaveFrom) {
    this.leaveFrom = leaveFrom;
  }

  public Date getLeaveTo() {
    return leaveTo;
  }

  public void setLeaveTo(Date leaveTo) {
    this.leaveTo = leaveTo;
  }

  public Double getLeaveDuration() {
    return leaveDuration;
  }

  public void setLeaveDuration(Double leaveDuration) {
    this.leaveDuration = leaveDuration;
  }

  public Double getNoOfWorkingDays() {
    return noOfWorkingDays;
  }

  public void setNoOfWorkingDays(Double noOfWorkingDays) {
    this.noOfWorkingDays = noOfWorkingDays;
  }

  public Date getApplicationDate() {
    return applicationDate;
  }

  public void setApplicationDate(Date applicationDate) {
    this.applicationDate = applicationDate;
  }

  public String getCommentByApplicant() {
    return commentByApplicant;
  }

  public void setCommentByApplicant(String commentByApplicant) {
    this.commentByApplicant = commentByApplicant;
  }

  public LeaveType getLeaveType() {
    return leaveType;
  }

  public void setLeaveType(LeaveType leaveType) {
    this.leaveType = leaveType;
  }

  public LeaveStatus getLeaveStatus() {
    return leaveStatus;
  }

  public void setLeaveStatus(LeaveStatus leaveStatus) {
    this.leaveStatus = leaveStatus;
  }

  public Long getIsBorrowed() {
    return isBorrowed;
  }

  public void setIsBorrowed(Long isBorrowed) {
    this.isBorrowed = isBorrowed;
  }

  public Long getManagerId() {
    return managerId;
  }

  public void setManagerId(Long managerId) {
    this.managerId = managerId;
  }

  public String getCommentByManager() {
    return commentByManager;
  }

  public void setCommentByManager(String commentByManager) {
    this.commentByManager = commentByManager;
  }

  public String getLeaveFromHalf() {
    return leaveFromHalf;
  }

  public void setLeaveFromHalf(String leaveFromHalf) {
    this.leaveFromHalf = leaveFromHalf;
  }

  public String getLeaveToHalf() {
    return leaveToHalf;
  }

  public void setLeaveToHalf(String leaveToHalf) {
    this.leaveToHalf = leaveToHalf;
  }

}
