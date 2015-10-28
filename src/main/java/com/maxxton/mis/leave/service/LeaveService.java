package com.maxxton.mis.leave.service;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.maxxton.mis.leave.domain.Employee;
import com.maxxton.mis.leave.domain.EmployeeLeave;
import com.maxxton.mis.leave.domain.LeaveApplication;
import com.maxxton.mis.leave.repository.EmployeeLeaveRepository;
import com.maxxton.mis.leave.repository.EmployeeRepository;
import com.maxxton.mis.leave.repository.LeaveApplicationRepository;
import com.maxxton.mis.leave.repository.LeaveStatusRepository;

@Service
public class LeaveService
{
  @Autowired
  private EmployeeRepository employeeRepository;
  
  @Autowired
  private EmployeeLeaveRepository employeeLeaveRepository;
  
  @Autowired
  private LeaveApplicationRepository leaveApplicationRepository;
  
  @Autowired
  private LeaveStatusRepository leaveStatusRepository;
  
  private final String LEAVE_STATUS_PENDING = "pending";
  /**
   * Simple method to return all the employees
   * @return
   */
  public Iterable<Employee> findEmployees()
  {
    return employeeRepository.findAll();
  }
  
  /**
   * To add leaves of a particular type for an employee by his/her manager.
   * @param employeeId
   * @param year
   * @param leaveCount
   * @param leaveTypeId
   * @return
   */
  public EmployeeLeave addEmployeeLeaves(Long employeeId, Long year, Double leaveCount, Long leaveTypeId)
  {    
    EmployeeLeave employeeLeave = new EmployeeLeave();
    employeeLeave.setEmployeeId(employeeId);
    employeeLeave.setYear(year);
    employeeLeave.setLeaveCount(leaveCount);
    employeeLeave.setLeaveTypeId(leaveTypeId);
    
    return employeeLeaveRepository.save(employeeLeave);
  }
  
  /**
   * Leave applied by an employee.
   * @param employeeId
   * @param leaveFrom
   * @param leaveTo
   * @param leaveTypeId
   * @param commentByApplicant
   * @param appliedFor
   */
  public void addAppliedLeave(Long employeeId, DateTime leaveFrom, DateTime leaveTo, Long leaveTypeId, String commentByApplicant, Long appliedFor)
  {
    LeaveApplication leaveApplication = new LeaveApplication();
    leaveApplication.setEmployeeId(employeeId);
    leaveApplication.setLeaveFrom(leaveFrom.toDate());
    leaveApplication.setLeaveTo(leaveTo.toDate());
    leaveApplication.setCommentByApplicant(commentByApplicant);
    leaveApplication.setLeaveTypeId(leaveTypeId);
    leaveApplication.setLeaveStatusId(leaveStatusRepository.findByName(LEAVE_STATUS_PENDING).getLeaveStatusId());
    
    System.out.println("leaveFrom: "+leaveFrom);
  }
}
