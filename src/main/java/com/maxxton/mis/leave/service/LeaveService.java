package com.maxxton.mis.leave.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxxton.mis.leave.domain.AppliedLeave;
import com.maxxton.mis.leave.domain.AppliedLeaveFrontend;
import com.maxxton.mis.leave.domain.AvailableLeaveCount;
import com.maxxton.mis.leave.domain.EmployeeLeave;
import com.maxxton.mis.leave.domain.LeaveStatus;
import com.maxxton.mis.leave.domain.LeaveType;
import com.maxxton.mis.leave.domain.PublicHoliday;
import com.maxxton.mis.leave.exception.InsufficientLeavesException;
import com.maxxton.mis.leave.repository.AppliedLeaveRepository;
import com.maxxton.mis.leave.repository.EmployeeLeaveRepository;
import com.maxxton.mis.leave.repository.LeaveStatusRepository;
import com.maxxton.mis.leave.repository.LeaveTypeRepository;
import com.maxxton.mis.leave.repository.PublicHolidayRepository;

@Service
public class LeaveService {

  @Autowired
  private EmployeeLeaveRepository employeeLeaveRepository;

  @Autowired
  private AppliedLeaveRepository appliedLeaveRepository;

  @Autowired
  private LeaveStatusRepository leaveStatusRepository;

  @Autowired
  private PublicHolidayRepository publicHolidayRepository;

  @Autowired
  private LeaveTypeRepository leaveTypeRepository;
  
  private static final String LEAVE_STATUS_PENDING = "Pending";
  private static final String LEAVE_STATUS_CANCELLED = "Cancelled";
  private static final String LEAVE_STATUS_REJECTED = "Rejected";
  
  private static final Double YEARLY_PLANNED = 16.0;
  
  private static final String PLANNED = "Planned";
  private static final String UNPLANNED = "Unplanned";
  private static final String COMPENSATORY_OFF = "Compensatory Off";
  private static final String BORROWED = "Borrowed";
  
  
  
  /**
   * Simple method to return all the applied leaves of an employee.
   * 
   * @param employeeId
   * @return List of applied leaves.
   */
  public List<AppliedLeaveFrontend> getAllAppliedLeaves(Long employeeId) {
    List<AppliedLeaveFrontend> response = new ArrayList<>();
    for(AppliedLeave leave: appliedLeaveRepository.findByEmployeeId(employeeId)) {
      AppliedLeaveFrontend leaveFrontend = new AppliedLeaveFrontend();
      leaveFrontend.copyFromAppliedLeave(leave);
      response.add(leaveFrontend);
    };
    
    return response;
  }

  /**
   * Method to return all applied leaves of an employee with status 'pending'.
   * 
   * @param employeeId
   * @return
   */
  public List<AppliedLeaveFrontend> getAllPendingLeaves(Long employeeId) {
    List<AppliedLeaveFrontend> response = new ArrayList<>();
    for(AppliedLeave leave: appliedLeaveRepository.findByEmployeeIdAndLeaveStatusNameIgnoreCase(employeeId, LEAVE_STATUS_PENDING)) {
      AppliedLeaveFrontend leaveFrontend = new AppliedLeaveFrontend();
      leaveFrontend.copyFromAppliedLeave(leave);
      response.add(leaveFrontend);
    };
    
    return response;
  }

  /**
   * Method to give count of available planned, unplanned and comp offs for an employee. Rules of borrowing leaves: 
   * i.   If a user is querying for leaves after July, there are no borrowables. All available planned leaves are listed as planned. 
   * ii.  If user is querying till July and he has more than 8 available planned leaves, than he has 8 borrowables and remaining leaves are available as planned leaves. 
   * iii. If a user has less than 8 planned leaves left, it means the user has already borrowed leaves from the second half of the year. So he cannot avail either planned or borrowable
   * leaves at this time. The remaining leaves will be available as planned leave after July.
   * 
   * @param employeeId
   * @return
   */
  public AvailableLeaveCount getAllAvailableLeaves(Long employeeId) {
    Calendar c = Calendar.getInstance();
    int currentYear = c.get(Calendar.YEAR);
    int currentMonth = c.get(Calendar.MONTH); 
    AvailableLeaveCount leaveCount = new AvailableLeaveCount();
    
    List<EmployeeLeave> leaves = employeeLeaveRepository.findByEmployeeIdAndYear(employeeId, Long.valueOf(currentYear));
    
    for(EmployeeLeave leave: leaves) {
      if(COMPENSATORY_OFF.equalsIgnoreCase(leave.getLeaveType().getName()))
        leaveCount.setCompOff(leave.getLeaveCount());
      else if(UNPLANNED.equalsIgnoreCase(leave.getLeaveType().getName()))
        leaveCount.setUnplanned(leave.getLeaveCount());
      else if(PLANNED.equalsIgnoreCase(leave.getLeaveType().getName())) {        
        if(currentMonth > Calendar.JULY) {
          leaveCount.setPlanned(leave.getLeaveCount());
          leaveCount.setBorrowable(0.0);
        }
        else {
          if(leave.getLeaveCount() >= YEARLY_PLANNED/2) {
            leaveCount.setPlanned(leave.getLeaveCount() - (YEARLY_PLANNED/2));
            leaveCount.setBorrowable(YEARLY_PLANNED/2);
          }
          else {
            leaveCount.setPlanned(0.0);
            leaveCount.setBorrowable(0.0);
          }
        }
      }
    }
    
    return leaveCount;
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
   * Method to add the leave applied by an employee. Borrowing leaves from future has not been implemented for now.
   * 
   * @param appliedLeave
   * @return appliedLeaveId of applied leave
   * @throws InsufficientLeavesException
   */
  public Long applyForLeave(AppliedLeaveFrontend appliedLeave) throws InsufficientLeavesException {
    Set<LocalDate> indiaHolidays = new HashSet<>();
    for (PublicHoliday holiday : publicHolidayRepository.findAll()) {
      indiaHolidays.add(new LocalDate(holiday.getHolidayDate()));
    }
    
    LocalDate leaveFromJoda = new LocalDate(appliedLeave.getLeaveFrom());
    
    String leaveTypeName = appliedLeave.getLeaveType().equalsIgnoreCase(BORROWED) ? PLANNED : appliedLeave.getLeaveType();
    LeaveType leaveType = leaveTypeRepository.findByNameIgnoreCase(leaveTypeName);
    LeaveStatus leaveStatus = leaveStatusRepository.findByName(appliedLeave.getLeaveStatus());
    AppliedLeave leaveApplication = appliedLeave.copyToAppliedLeave();
    leaveApplication.setLeaveType(leaveType);
    leaveApplication.setLeaveStatus(leaveStatus);

    // update employee leaves
    if(PLANNED.equalsIgnoreCase(leaveTypeName) || UNPLANNED.equalsIgnoreCase(leaveTypeName) || COMPENSATORY_OFF.equalsIgnoreCase(leaveTypeName)) {
      EmployeeLeave employeeLeave = employeeLeaveRepository.findByEmployeeIdAndLeaveTypeLeaveTypeIdAndYear(appliedLeave.getEmployeeId(), leaveType.getLeaveTypeId(), Long.valueOf(leaveFromJoda.getYear()));
      Double newCount = employeeLeave.getLeaveCount() - appliedLeave.getNoOfWorkingDays();
      if(newCount < 0)
        throw new InsufficientLeavesException();
      employeeLeave.setLeaveCount(newCount);
      employeeLeaveRepository.save(employeeLeave);
    }
    
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
  public Long processAppliedLeave(Long managerId, Long appliedLeaveId, Long leaveStatusId, String commentByManager) {
    AppliedLeave leaveApplication = null;//appliedLeaveRepository.findByAppliedLeaveId(appliedLeaveId);

//    leaveApplication.setLeaveStatusId(leaveStatusId);
    if (managerId != null)
      leaveApplication.setManagerId(managerId);
    if (commentByManager != null)
      leaveApplication.setCommentByManager(commentByManager);
    appliedLeaveRepository.save(leaveApplication);

    // if leaves were cancelled or rejected, their count must be added back to employee leave count.
    if (leaveStatusId == leaveStatusRepository.findByName(LEAVE_STATUS_REJECTED).getLeaveStatusId() || leaveStatusId == leaveStatusRepository.findByName(LEAVE_STATUS_CANCELLED).getLeaveStatusId()) {
      LocalDate leaveFrom = new LocalDate(leaveApplication.getLeaveFrom());
      EmployeeLeave employeeLeave = null;//employeeLeaveRepository.findByEmployeeIdAndLeaveTypeIdAndYear(leaveApplication.getEmployeeId(), leaveApplication.getLeaveTypeId(), new Long(leaveFrom.getYear()));
      employeeLeave.setLeaveCount(employeeLeave.getLeaveCount() + leaveApplication.getNoOfWorkingDays());
      employeeLeaveRepository.save(employeeLeave);
    }

    return appliedLeaveId;
  }
  
}
