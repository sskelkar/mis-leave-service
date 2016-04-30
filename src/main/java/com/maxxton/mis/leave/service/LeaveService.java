package com.maxxton.mis.leave.service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxxton.mis.leave.domain.AppliedLeave;
import com.maxxton.mis.leave.domain.AvailableLeaveCount;
import com.maxxton.mis.leave.domain.EmployeeLeave;
import com.maxxton.mis.leave.domain.PublicHoliday;
import com.maxxton.mis.leave.domain.enumeration.LeaveStatus;
import com.maxxton.mis.leave.domain.enumeration.LeaveType;
import com.maxxton.mis.leave.exception.InsufficientLeavesException;
import com.maxxton.mis.leave.repository.AppliedLeaveRepository;
import com.maxxton.mis.leave.repository.EmployeeLeaveRepository;
import com.maxxton.mis.leave.repository.PublicHolidayRepository;

@Service
public class LeaveService {

  @Autowired
  private EmployeeLeaveRepository employeeLeaveRepository;

  @Autowired
  private AppliedLeaveRepository appliedLeaveRepository;

  @Autowired
  private PublicHolidayRepository publicHolidayRepository;

  private static final String INDIA_TIMEZONE = "Asia/Kolkata";

  private static final List<LeaveType> allApplicableLeaveTypes = new ArrayList<>();
  private static final List<LeaveStatus> allLeaveStatuses = new ArrayList<>();
  
  /**
   * Simple method to return all the applied leaves of an employee.
   * 
   * @param employeeId
   * @return List of applied leaves.
   */
  public List<AppliedLeave> getAllAppliedLeaves(Long employeeId) {
    return appliedLeaveRepository.findByEmployeeId(employeeId);
  }

  /**
   * Method to return all applied leaves of an employee with status 'pending'.
   * 
   * @param employeeId
   * @return
   */
  public List<AppliedLeave> getAllPendingLeaves(Long employeeId) {
    return null;//appliedLeaveRepository.findByEmployeeIdAndLeaveStatusId(employeeId, leaveStatusRepository.findByName(LEAVE_STATUS_PENDING).getLeaveStatusId());
  }

  public AvailableLeaveCount getAllAvailableLeaves(Long employeeId) {
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    AvailableLeaveCount leaveCount = new AvailableLeaveCount();
    
    List<EmployeeLeave> leaves = employeeLeaveRepository.findByEmployeeIdAndYearGreaterThanEqual(employeeId, Long.valueOf(currentYear));
    
    leaves.forEach(leave -> {
      if(leave.getLeaveType() == LeaveType.COMPENSATORY_OFF)
        leaveCount.setCompOff(leaveCount.getCompOff() + leave.getLeaveCount());
      else if(leave.getLeaveType() == LeaveType.UNPLANNED)
        leaveCount.setUnplanned(leaveCount.getUnplanned() + leave.getLeaveCount());
      else if(leave.getLeaveType() == LeaveType.PLANNED)
        leaveCount.setPlanned(leaveCount.getPlanned() + leave.getLeaveCount());      
    });
    
    return leaveCount;
  }
  
  public Map<LeaveType, Double> getAllAvailableLeavesNew(Long employeeId) {
	    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
	    
	    List<EmployeeLeave> leaves = employeeLeaveRepository.findByEmployeeIdAndYearGreaterThanEqual(employeeId, Long.valueOf(currentYear));
	    
	    Map<LeaveType, Double> countForLeaveType = new HashMap<>();

	    for(EmployeeLeave employeeLeave : leaves)
	    {
	      countForLeaveType.put(employeeLeave.getLeaveType(), 
	        		(countForLeaveType.get(employeeLeave.getLeaveType()) != null ? countForLeaveType.get(employeeLeave.getLeaveType()) : 0.0) + employeeLeave.getLeaveCount());
	    }
	    
	    return countForLeaveType;
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
//    employeeLeave.setLeaveTypeId(leaveTypeId);

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
    EmployeeLeave employeeLeave = null;//employeeLeaveRepository.findByEmployeeIdAndLeaveTypeIdAndYear(employeeId, leaveTypeId, new Long(leaveFromJoda.getYear()));
    Double availableLeaves = employeeLeave.getLeaveCount();
    if (availableLeaves < businessDays)
      throw new InsufficientLeavesException();

    AppliedLeave leaveApplication = new AppliedLeave();
    leaveApplication.setEmployeeId(employeeId);
    leaveApplication.setLeaveFrom(leaveFrom);
    leaveApplication.setLeaveTo(leaveTo);
    leaveApplication.setCommentByApplicant(commentByApplicant);
//    leaveApplication.setLeaveTypeId(leaveTypeId);
//    leaveApplication.setLeaveStatusId(leaveStatusRepository.findByName(LEAVE_STATUS_PENDING).getLeaveStatusId());
    leaveApplication.setLeaveDuration(daysElapsed);
    leaveApplication.setNoOfWorkingDays(businessDays);

    LocalDate today = new LocalDate(DateTimeZone.forID(INDIA_TIMEZONE));
    leaveApplication.setApplicationDate(today.toDate());

    // update employee leaves
    employeeLeave.setLeaveCount(availableLeaves - businessDays);
    employeeLeaveRepository.save(employeeLeave);

    // save the applied leave
    AppliedLeave savedLeave = appliedLeaveRepository.save(leaveApplication);
    return savedLeave.getAppliedLeaveId();
  }

  /**
   * Method to process an applied leave. Meaning the employee who had applied for a leave can cancel them himself. Or his manager can either approve or reject the leaves.
   * 
   * @param managerId
   *          Employee id of approving/rejecting employee. This will be null if an employee is cancelling his/her own leaves.
   * @param appliedLeaveId
   * @param leaveStatusId
   * @param commentByManager
   * @return leaveApplicationId of the processed leave.
   */
  public Long processAppliedLeave(Long managerId, Long appliedLeaveId, LeaveStatus leaveStatus, String commentByManager) {
    AppliedLeave leaveApplication = null;//appliedLeaveRepository.findByAppliedLeaveId(appliedLeaveId);

//    leaveApplication.setLeaveStatusId(leaveStatusId);
    if (managerId != null)
      leaveApplication.setManagerId(managerId);
    if (commentByManager != null)
      leaveApplication.setCommentByManager(commentByManager);
    appliedLeaveRepository.save(leaveApplication);

    // if leaves were cancelled or rejected, their count must be added back to employee leave count.
    if (leaveStatus == LeaveStatus.REJECTED || leaveStatus == LeaveStatus.CANCELLED) {
      LocalDate leaveFrom = new LocalDate(leaveApplication.getLeaveFrom());
      EmployeeLeave employeeLeave = null;//employeeLeaveRepository.findByEmployeeIdAndLeaveTypeIdAndYear(leaveApplication.getEmployeeId(), leaveApplication.getLeaveTypeId(), new Long(leaveFrom.getYear()));
      employeeLeave.setLeaveCount(employeeLeave.getLeaveCount() + leaveApplication.getNoOfWorkingDays());
      employeeLeaveRepository.save(employeeLeave);
    }

    return appliedLeaveId;
  } 
  
  public List<LeaveType> getAllApplicableLeaveTypes() {
//    if(allApplicableLeaveTypes.isEmpty()) {      
//      leaveTypeRepository.findAll().forEach(leave -> {
//        if(!(LeaveType.ENCASHED == leave || LeaveType.CARRY_FORWARD == leave))
//          allApplicableLeaveTypes.add(leave);        
//      });
//    }
    
    for (LeaveType type : LeaveType.values()) {
    	if(!(LeaveType.ENCASHED == type || LeaveType.CARRY_FORWARD == type))
            allApplicableLeaveTypes.add(type);
        }
      
    
    return allApplicableLeaveTypes;
  }
  
  public List<LeaveStatus> getAllLeaveStatuses() {
//    if(allLeaveStatuses.isEmpty()) {
//      leaveStatusRepository.findAll().forEach(status -> {
//        allLeaveStatuses.add(status);
//      });      
//    }
    
    for (LeaveStatus type : LeaveStatus.values()) {
    	allLeaveStatuses.add(type);
        }
    return allLeaveStatuses;
  }
  
  public Iterable<PublicHoliday> getAllPublicHoliday() {
		return publicHolidayRepository.findAll(); 
	  }
}
