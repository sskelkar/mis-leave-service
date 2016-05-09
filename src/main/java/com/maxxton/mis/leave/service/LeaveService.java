package com.maxxton.mis.leave.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxxton.mis.leave.domain.AppliedLeave;
import com.maxxton.mis.leave.domain.AppliedLeaveFrontend;
import com.maxxton.mis.leave.domain.AvailableLeaveCount;
import com.maxxton.mis.leave.domain.EmployeeLeave;
import com.maxxton.mis.leave.domain.PublicHoliday;
import com.maxxton.mis.leave.domain.enumeration.LeaveStatus;
import com.maxxton.mis.leave.domain.enumeration.LeaveType;
import com.maxxton.mis.leave.exception.InsufficientLeavesException;
import com.maxxton.mis.leave.exception.LeaveOverlapException;
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

  private static final Double YEARLY_PLANNED = 16.0;

  private final String INDIA_TIMEZONE = "Asia/Kolkata";

  private static final List<LeaveType> allApplicableLeaveTypes = new ArrayList<>();
  private static final List<LeaveStatus> allLeaveStatuses = new ArrayList<>();

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
    for(AppliedLeave leave: appliedLeaveRepository.findByEmployeeIdAndLeaveStatus(employeeId, LeaveStatus.PENDING)) {
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


//    List<EmployeeLeave> leaves = employeeLeaveRepository.findByEmployeeIdAndYearGreaterThanEqual(employeeId, Long.valueOf(currentYear));

    leaves.forEach(leave -> {
      if(leave.getLeaveType() == LeaveType.COMPENSATORY_OFF)
        leaveCount.setCompOff(leaveCount.getCompOff() + leave.getLeaveCount());
      else if(leave.getLeaveType() == LeaveType.UNPLANNED)
        leaveCount.setUnplanned(leaveCount.getUnplanned() + leave.getLeaveCount());
      else if(leave.getLeaveType() == LeaveType.PLANNED) {
        leaveCount.setPlanned(leaveCount.getPlanned() + leave.getLeaveCount());
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
        
    });

    return leaveCount;
  }
  /**
   * To add leaves of a particular type for an employee by his/her manager.
   *
   * @param employeeId
   * @param year
   * @param leaveCount
   * @param leaveType
   * @return employeeLeaveId of the added leave.
   */
  public Long addEmployeeLeaves(Long employeeId, Long year, Double leaveCount, LeaveType leaveType) {
    EmployeeLeave employeeLeave = new EmployeeLeave();
    employeeLeave.setEmployeeId(employeeId);
    employeeLeave.setYear(year);
    employeeLeave.setLeaveCount(leaveCount);
    employeeLeave.setLeaveType(leaveType);

    return employeeLeaveRepository.save(employeeLeave).getEmployeeLeaveId();
  }

  /**
   * Method to add the leave applied by an employee. Borrowing leaves from future has not been implemented for now.
   *
   * @param appliedLeave
   * @return appliedLeaveId of applied leave
   * @throws InsufficientLeavesException
   * @throws LeaveOverlapException 
   */
  //TODO make sure the time information from dates are truncated before insertion
  public Long applyForLeave(AppliedLeaveFrontend appliedLeave) throws LeaveOverlapException, InsufficientLeavesException {
    LocalDate leaveFromJoda = new LocalDate(appliedLeave.getLeaveFrom());

    LeaveType leaveType = appliedLeave.getLeaveType().getValue().equalsIgnoreCase("BORROWED") ? LeaveType.PLANNED : appliedLeave.getLeaveType();
    AppliedLeave leaveApplication = appliedLeave.copyToAppliedLeave();
    leaveApplication.setLeaveType(appliedLeave.getLeaveType());
    leaveApplication.setLeaveStatus(appliedLeave.getLeaveStatus());

    // check if the leave being applied overlaps with any existing leaves
    List<AppliedLeave> overlappingLeaves = appliedLeaveRepository.findOverlappingLeaves(appliedLeave.getEmployeeId(), appliedLeave.getLeaveFrom(), appliedLeave.getLeaveFromHalf(),
                                                                                                       appliedLeave.getLeaveTo(), appliedLeave.getLeaveToHalf()); //, Arrays.asList("Pending","Approved")
    if(!overlappingLeaves.isEmpty())
      throw new LeaveOverlapException("Overlapping leaves found: " + overlappingLeaves.size());
    
    // update employee leaves
    if(LeaveType.PLANNED == leaveType || LeaveType.UNPLANNED == leaveType || LeaveType.COMPENSATORY_OFF == leaveType) {
      EmployeeLeave employeeLeave = employeeLeaveRepository.findByEmployeeIdAndLeaveTypeAndYear(appliedLeave.getEmployeeId(), leaveType, Long.valueOf(leaveFromJoda.getYear()));
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
      EmployeeLeave employeeLeave = employeeLeaveRepository.findByEmployeeIdAndLeaveTypeAndYear(leaveApplication.getEmployeeId(), leaveApplication.getLeaveType(), new Long(leaveFrom.getYear()));
      employeeLeave.setLeaveCount(employeeLeave.getLeaveCount() + leaveApplication.getNoOfWorkingDays());
      employeeLeaveRepository.save(employeeLeave);
    }

    return appliedLeaveId;
  }

  public Iterable<PublicHoliday> getAllPublicHoliday() {
    return publicHolidayRepository.findAll(); 
    }
  
  public List<AppliedLeave> getAppliedLeaveHistory(Long employeeId){
    Date appliedDate = new DateTime(DateTimeZone.forID(INDIA_TIMEZONE)).toLocalDateTime().toDate();
    return appliedLeaveRepository.findByEmployeeIdAndApplicationDateLessThanEqualAndLeaveStatusNot(employeeId, appliedDate, LeaveStatus.PENDING);
  }
  
  public List<LeaveStatus> getAllLeaveStatuses()
  {
    for (LeaveStatus status : LeaveStatus.values())  {
      allLeaveStatuses.add(status);
    }
    return allLeaveStatuses;
  }

  public List<LeaveType> getAllApplicableLeaveTypes()
  {
    for (LeaveType type : LeaveType.values()) {
      if (!(LeaveType.ENCASHED == type || LeaveType.CARRY_FORWARD == type))
        allApplicableLeaveTypes.add(type);
    }
    return allApplicableLeaveTypes;
  }

  public List<AppliedLeave> getPendingLeavesForNextWorkingDay()
  {
    Calendar nextWorkingDay = GregorianCalendar.getInstance();
    nextWorkingDay.setTime(new Date());
    nextWorkingDay.add(Calendar.DATE, nextWorkingDay.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY ? 3 : 1);
    return appliedLeaveRepository.findByLeaveFromAndLeaveStatus(nextWorkingDay.getTime(), LeaveStatus.PENDING);
  }
}