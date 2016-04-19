package com.maxxton.mis.leave.service;

import java.time.DayOfWeek;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxxton.mis.leave.domain.EmployeeLeave;
import com.maxxton.mis.leave.domain.LeaveApplication;
import com.maxxton.mis.leave.domain.PublicHoliday;
import com.maxxton.mis.leave.exception.InsufficientLeavesException;
import com.maxxton.mis.leave.repository.EmployeeLeaveRepository;
import com.maxxton.mis.leave.repository.LeaveApplicationRepository;
import com.maxxton.mis.leave.repository.LeaveStatusRepository;
import com.maxxton.mis.leave.repository.PublicHolidayRepository;

@Service
public class LeaveService {

  @Autowired
  private EmployeeLeaveRepository employeeLeaveRepository;

  @Autowired
  private LeaveApplicationRepository leaveApplicationRepository;

  @Autowired
  private LeaveStatusRepository leaveStatusRepository;

  @Autowired
  private PublicHolidayRepository publicHolidayRepository;

  private static final String LEAVE_STATUS_PENDING = "pending";
  private static final String LEAVE_STATUS_CANCELLED = "cancelled";
  private static final String LEAVE_STATUS_REJECTED = "rejected";
  private static final String INDIA_TIMEZONE = "Asia/Kolkata";

  /**
   * Simple method to return all the applied leaves of an employee.
   * 
   * @param employeeId
   * @return List of applied leaves.
   */
  public Iterable<LeaveApplication> getAllAppliedLeaves(Long employeeId) {
    return leaveApplicationRepository.findByEmployeeId(employeeId);
  }

  /**
   * Method to return all applied leaves of an employee with status 'pending'.
   * 
   * @param employeeId
   * @return
   */
  public Iterable<LeaveApplication> getAllPendingLeaves(Long employeeId) {
    return leaveApplicationRepository.findByEmployeeIdAndLeaveStatusId(employeeId, leaveStatusRepository.findByName(LEAVE_STATUS_PENDING).getLeaveStatusId());
  }

  /**
   * To add leaves of a particular type for an employee by his/her manager.
   * 
   * @param employeeId
   * @param year
   * @param leaveCount
   * @param leaveTypeId
   * @return employeeLeaveId of the added leave.
   */
  public Long addEmployeeLeaves(Long employeeId, Long year, Double leaveCount, Long leaveTypeId) {
    EmployeeLeave employeeLeave = new EmployeeLeave();
    employeeLeave.setEmployeeId(employeeId);
    employeeLeave.setYear(year);
    employeeLeave.setLeaveCount(leaveCount);
    employeeLeave.setLeaveTypeId(leaveTypeId);

    return employeeLeaveRepository.save(employeeLeave).getEmployeeLeaveId();
  }

  /**
   * Method to add the leave applied by an employee. Borrowing leaves from future and applying for half day has not been implemented for now.
   * 
   * @param employeeId
   * @param leaveFrom
   * @param leaveTo
   * @param leaveTypeId
   * @param commentByApplicant
   * @param appliedFor
   * @return leaveApplicationId of applied leave
   * @throws InsufficientLeavesException
   */
  public Long applyForLeave(Long employeeId, Date leaveFrom, Date leaveTo, Long leaveTypeId, String commentByApplicant, Long appliedFor) throws InsufficientLeavesException {
    Set<LocalDate> indiaHolidays = new HashSet<LocalDate>();
    for (PublicHoliday holiday : publicHolidayRepository.findAll()) {
      indiaHolidays.add(new LocalDate(holiday.getHolidayDate()));
    }

    // calculate total number of all leave days (including from and to days) and total number of business days
    Double businessDays = 0.0;
    Double daysElapsed = 0.0;
    LocalDate leaveFromJoda = new LocalDate(leaveFrom);
    LocalDate leaveToJoda = new LocalDate(leaveTo);
    for (LocalDate date = leaveFromJoda; date.compareTo(leaveToJoda) <= 0; date = date.plusDays(1)) {
      daysElapsed++;
      if (!(date.getDayOfWeek() == DayOfWeek.SATURDAY.getValue() || date.getDayOfWeek() == DayOfWeek.SUNDAY.getValue() || indiaHolidays.contains(date)))
        businessDays++;
    }

    // check if sufficient number of leaves are present for this employee of this leave type
    EmployeeLeave employeeLeave = employeeLeaveRepository.findByEmployeeIdAndLeaveTypeIdAndYear(employeeId, leaveTypeId, new Long(leaveFromJoda.getYear()));
    Double availableLeaves = employeeLeave.getLeaveCount();
    if (availableLeaves < businessDays)
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

    // update employee leaves
    employeeLeave.setLeaveCount(availableLeaves - businessDays);
    employeeLeaveRepository.save(employeeLeave);

    // save the applied leave
    LeaveApplication savedLeave = leaveApplicationRepository.save(leaveApplication);
    return savedLeave.getLeaveApplicationId();
  }

  /**
   * Method to process an applied leave. Meaning the employee who had applied for a leave can cancel them himself. Or his manager can either approve or reject the leaves.
   * 
   * @param managerId
   *          Employee id of approving/rejecting employee. This will be null if an employee is cancelling his/her own leaves.
   * @param leaveApplicationId
   * @param leaveStatusId
   * @param commentByManager
   * @return leaveApplicationId of the processed leave.
   */
  public Long processAppliedLeave(Long managerId, Long leaveApplicationId, Long leaveStatusId, String commentByManager) {
    LeaveApplication leaveApplication = leaveApplicationRepository.findByLeaveApplicationId(leaveApplicationId);

    leaveApplication.setLeaveStatusId(leaveStatusId);
    if (managerId != null)
      leaveApplication.setManagerId(managerId);
    if (commentByManager != null)
      leaveApplication.setCommentByManager(commentByManager);
    leaveApplicationRepository.save(leaveApplication);

    // if leaves were cancelled or rejected, their count must be added back to employee leave count.
    if (leaveStatusId == leaveStatusRepository.findByName(LEAVE_STATUS_REJECTED).getLeaveStatusId() || leaveStatusId == leaveStatusRepository.findByName(LEAVE_STATUS_CANCELLED).getLeaveStatusId()) {
      LocalDate leaveFrom = new LocalDate(leaveApplication.getLeaveFrom());
      EmployeeLeave employeeLeave = employeeLeaveRepository.findByEmployeeIdAndLeaveTypeIdAndYear(leaveApplication.getEmployeeId(), leaveApplication.getLeaveTypeId(), new Long(leaveFrom.getYear()));
      employeeLeave.setLeaveCount(employeeLeave.getLeaveCount() + leaveApplication.getNoOfWorkingDays());
      employeeLeaveRepository.save(employeeLeave);
    }

    return leaveApplicationId;
  }
}
