package com.maxxton.mis.leave.service;

import java.time.DayOfWeek;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxxton.mis.leave.domain.Employee;
import com.maxxton.mis.leave.domain.EmployeeLeave;
import com.maxxton.mis.leave.domain.LeaveApplication;
import com.maxxton.mis.leave.domain.PublicHoliday;
import com.maxxton.mis.leave.exception.InsufficientLeavesException;
import com.maxxton.mis.leave.repository.EmployeeLeaveRepository;
import com.maxxton.mis.leave.repository.EmployeeRepository;
import com.maxxton.mis.leave.repository.LeaveApplicationRepository;
import com.maxxton.mis.leave.repository.LeaveStatusRepository;
import com.maxxton.mis.leave.repository.PublicHolidayRepository;

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
  
  @Autowired
  private PublicHolidayRepository publicHolidayRepository;
  
  private final String LEAVE_STATUS_PENDING = "pending";
  private final String INDIA_TIMEZONE = "Asia/Kolkata";
    
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
   * @throws InsufficientLeavesException 
   */
  public void addAppliedLeave(Long employeeId, Date leaveFrom, Date leaveTo, Long leaveTypeId, String commentByApplicant, Long appliedFor) throws InsufficientLeavesException
  {    
    Set<LocalDate> indiaHolidays = new HashSet<LocalDate>();
    for(PublicHoliday holiday : publicHolidayRepository.findAll()) {
      indiaHolidays.add(new LocalDate(holiday.getHolidayDate()));
    }
    
    // calculate total number of all leave days (including from and to days) and total number of business days
    Double businessDays = 0.0;
    Double daysElapsed = 0.0;
    LocalDate leaveFromJoda = new LocalDate(leaveFrom);
    LocalDate leaveToJoda = new LocalDate(leaveTo);
    for(LocalDate date = leaveFromJoda; date.compareTo(leaveToJoda) <= 0; date = date.plusDays(1)) {
      daysElapsed++;
      if(!(date.getDayOfWeek() == DayOfWeek.SATURDAY.getValue() || date.getDayOfWeek() == DayOfWeek.SUNDAY.getValue() || indiaHolidays.contains(date)))
        businessDays++;
    }    
    
    // check if sufficient number of leaves are present for this employee of this leave type
    EmployeeLeave employeeLeave = employeeLeaveRepository.findByEmployeeIdAndLeaveTypeIdAndYear(employeeId, leaveTypeId, new Long(leaveFromJoda.getYear()));
    Double availableLeaves = employeeLeave.getLeaveCount();
    if(availableLeaves < businessDays)
      throw new InsufficientLeavesException();
    
    LeaveApplication leaveApplication = new LeaveApplication();
    leaveApplication.setEmployeeId(employeeId);
    leaveApplication.setLeaveFrom(leaveFrom);
    leaveApplication.setLeaveTo(leaveTo);
    leaveApplication.setCommentByApplicant(commentByApplicant);
    leaveApplication.setLeaveTypeId(leaveTypeId);
    leaveApplication.setLeaveStatusId(leaveStatusRepository.findByName(LEAVE_STATUS_PENDING).getLeaveStatusId());
    leaveApplication.setLeaveDuration(daysElapsed);
    leaveApplication.setNoOfWorkingDays(businessDays);
    
    LocalDate today = new LocalDate(DateTimeZone.forID(INDIA_TIMEZONE));    
    leaveApplication.setApplicationDate(today.toDate());
    
    // save the applied leave 
    leaveApplicationRepository.save(leaveApplication);
    
    // update employee leaves
    employeeLeave.setLeaveCount(availableLeaves - businessDays);
    employeeLeaveRepository.save(employeeLeave);
  }
}
