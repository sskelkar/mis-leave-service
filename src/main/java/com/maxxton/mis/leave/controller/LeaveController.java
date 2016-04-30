package com.maxxton.mis.leave.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maxxton.mis.leave.domain.AppliedLeaveFrontend;
import com.maxxton.mis.leave.domain.AvailableLeaveCount;
import com.maxxton.mis.leave.domain.PublicHoliday;
import com.maxxton.mis.leave.exception.InsufficientLeavesException;
import com.maxxton.mis.leave.exception.LeaveOverlapException;
import com.maxxton.mis.leave.service.LeaveService;


@RestController
@RequestMapping(value = "/mis/leave")
public class LeaveController {

  @Autowired
  LeaveService leaveService;

  @RequestMapping(method = RequestMethod.GET, value = "/applied")
  public List<AppliedLeaveFrontend> getAllAppliedLeaves(@RequestParam Long employeeId) {
    return leaveService.getAllAppliedLeaves(employeeId);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/available")
  public AvailableLeaveCount getAllAvailableLeaves(@RequestParam Long employeeId) {
    return leaveService.getAllAvailableLeaves(employeeId);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/pending")
  public List<AppliedLeaveFrontend> getAllPendingLeaves(@RequestParam Long employeeId) {
    return leaveService.getAllPendingLeaves(employeeId);
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/")
  public Long addEmployeeLeaves(@RequestParam Long employeeId, @RequestParam Long year, @RequestParam Double leaveCount, @RequestParam Long leaveTypeId) {
    return leaveService.addEmployeeLeaves(employeeId, year, leaveCount, leaveTypeId);
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/apply")
  public Long applyForLeave(@RequestBody AppliedLeaveFrontend appliedLeave) throws InsufficientLeavesException, LeaveOverlapException {
    return leaveService.applyForLeave(appliedLeave);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/process")
  public Long processAppliedLeave(@RequestParam(value = "managerId", required = false) Long managerId, @RequestParam Long leaveApplicationId, @RequestParam Long leaveStatusId,
                                  @RequestParam(value = "commentByManager", required = false) String commentByManager) {
    return leaveService.processAppliedLeave(managerId, leaveApplicationId, leaveStatusId, commentByManager);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/holiday")
  public Iterable<PublicHoliday> getAllPublicHoliday() {
    return leaveService.getAllPublicHoliday();
  }
  @RequestMapping(method = RequestMethod.GET, value = "/history")
  public List<AppliedLeave> getAppliedHistory(@RequestParam(value="employeeId") Long employeeId){
  	return leaveService.getAppliedLeaveHistory(employeeId);
  }
}
