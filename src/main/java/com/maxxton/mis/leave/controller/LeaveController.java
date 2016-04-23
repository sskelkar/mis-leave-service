package com.maxxton.mis.leave.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maxxton.mis.leave.domain.AppliedLeave;
import com.maxxton.mis.leave.domain.AvailableLeaveCount;
import com.maxxton.mis.leave.domain.LeaveStatus;
import com.maxxton.mis.leave.domain.LeaveType;
import com.maxxton.mis.leave.exception.InsufficientLeavesException;
import com.maxxton.mis.leave.service.LeaveService;


@RestController
@RequestMapping(value = "/mis/leave")
public class LeaveController {

  @Autowired  
  LeaveService leaveService;

  @RequestMapping(method = RequestMethod.GET, value = "/types")
  public List<String> getAllLeavesTypes() {
    return leaveService.getAllApplicableLeaveTypes();
  }
  
  @RequestMapping(method = RequestMethod.GET, value = "/statuses")
  public List<String> getAllLeavesStatuses() {
    return leaveService.getAllLeaveStatuses();
  }  
    
  @RequestMapping(method = RequestMethod.GET, value = "/applied")
  public List<AppliedLeave> getAllAppliedLeaves(@RequestParam Long employeeId) {
    return leaveService.getAllAppliedLeaves(employeeId);
  }
  
  @RequestMapping(method = RequestMethod.GET, value = "/available")
  public AvailableLeaveCount getAllAvailableLeaves(@RequestParam Long employeeId) {
    return leaveService.getAllAvailableLeaves(employeeId);
  }
  
  @RequestMapping(method = RequestMethod.GET, value = "/application/pending")
  public List<AppliedLeave> getAllPendingLeaves(@RequestParam Long employeeId) {
    return leaveService.getAllPendingLeaves(employeeId);
  }
  
  @RequestMapping(method = RequestMethod.PUT, value = "/")
  public Long addEmployeeLeaves(@RequestParam Long employeeId, @RequestParam Long year, @RequestParam Double leaveCount, @RequestParam Long leaveTypeId) {
    return leaveService.addEmployeeLeaves(employeeId, year, leaveCount, leaveTypeId);
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/application")
  public Long applyForLeave(@RequestParam Long employeeId, @RequestParam Date leaveFrom, @RequestParam Date leaveTo, @RequestParam Long leaveTypeId, @RequestParam String commentByApplicant,
                            @RequestParam Long appliedFor) throws InsufficientLeavesException {
    return leaveService.applyForLeave(employeeId, leaveFrom, leaveTo, leaveTypeId, commentByApplicant, appliedFor);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/application")
  public Long processAppliedLeave(@RequestParam(value = "managerId", required = false) Long managerId, @RequestParam Long leaveApplicationId, @RequestParam Long leaveStatusId,
                                  @RequestParam(value = "commentByManager", required = false) String commentByManager) {
    return leaveService.processAppliedLeave(managerId, leaveApplicationId, leaveStatusId, commentByManager);
  } 
}
