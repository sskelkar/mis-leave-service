package com.maxxton.mis.leave.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * This class is equivalent to AppliedLeave class. But it has the fields that are useful for frontend.
 * 
 * Maxxton Group 2016
 *
 * @author S.Kelkar(s.kelkar@maxxton.com)
 */
public class AppliedLeaveFrontend {

  private Long appliedLeaveId;
  private Long employeeId;
  private String employeeName;
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date leaveFrom;
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date leaveTo;
  private Double leaveDuration;
  private Double noOfWorkingDays;
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date applicationDate;
  private String commentByApplicant;
  private String commentByManager;
  private Long managerId;
  private String leaveFromHalf;
  private String leaveToHalf;
  private String leaveType;
  private String leaveStatus;
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
  public String getEmployeeName() {
    return employeeName;
  }
  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
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
  public String getCommentByManager() {
    return commentByManager;
  }
  public void setCommentByManager(String commentByManager) {
    this.commentByManager = commentByManager;
  }
  public Long getManagerId() {
    return managerId;
  }
  public void setManagerId(Long managerId) {
    this.managerId = managerId;
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
  public String getLeaveType() {
    return leaveType;
  }
  public void setLeaveType(String leaveType) {
    this.leaveType = leaveType;
  }
  public String getLeaveStatus() {
    return leaveStatus;
  }
  public void setLeaveStatus(String leaveStatus) {
    this.leaveStatus = leaveStatus;
  }

  /**
   * Method to copy all the fields into the current object from a given AppliedLeave object.
   * @param appliedLeave
   */
  public void copyFromAppliedLeave(AppliedLeave appliedLeave) {
    this.appliedLeaveId = appliedLeave.getAppliedLeaveId();
    this.employeeId = appliedLeave.getEmployeeId();
    this.leaveFrom = appliedLeave.getLeaveFrom();
    this.leaveTo = appliedLeave.getLeaveTo();
    this.leaveDuration = appliedLeave.getLeaveDuration();
    this.noOfWorkingDays = appliedLeave.getNoOfWorkingDays();
    this.applicationDate = appliedLeave.getApplicationDate();
    this.commentByApplicant = appliedLeave.getCommentByApplicant();
    this.commentByManager = appliedLeave.getCommentByManager();
    this.managerId = appliedLeave.getManagerId();
    this.leaveFromHalf = appliedLeave.getLeaveFromHalf();
    this.leaveToHalf = appliedLeave.getLeaveToHalf();
    this.leaveType = appliedLeave.getLeaveType().getName();
    this.leaveStatus = appliedLeave.getLeaveStatus().getName();
  }
  
  /**
   * Method to create an AppliedLeave object using current AppliedLeaveFrontend object. LeaveType and LeaveStatus must be set from the service class.
   * @return
   */
  public AppliedLeave copyToAppliedLeave() {
    AppliedLeave appliedLeave = new AppliedLeave();
    appliedLeave.setAppliedLeaveId(this.appliedLeaveId);
    appliedLeave.setEmployeeId(this.employeeId);
    appliedLeave.setLeaveFrom(this.leaveFrom);
    appliedLeave.setLeaveTo(this.leaveTo);
    appliedLeave.setLeaveDuration(this.leaveDuration);
    appliedLeave.setNoOfWorkingDays(this.noOfWorkingDays);
    appliedLeave.setApplicationDate(this.applicationDate);
    appliedLeave.setCommentByApplicant(this.commentByApplicant);
    appliedLeave.setCommentByManager(this.commentByManager);
    appliedLeave.setManagerId(this.managerId);
    appliedLeave.setLeaveFromHalf(this.leaveFromHalf);
    appliedLeave.setLeaveToHalf(this.leaveToHalf);
    
    // LeaveType and LeaveStatus must be set from the service class
    return appliedLeave;
  }
}
