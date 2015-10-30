package com.maxxton.mis.leave.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maxxton.mis.leave.domain.Employee;
import com.maxxton.mis.leave.domain.EmployeeLeave;
import com.maxxton.mis.leave.domain.LeaveApplication;
import com.maxxton.mis.leave.exception.InsufficientLeavesException;
import com.maxxton.mis.leave.service.LeaveService;

@RestController
@RequestMapping(value = "/mis")
public class LeaveController {
  @Autowired
  LeaveService leaveService;

  @RequestMapping(method = RequestMethod.GET, value = "/employee")
  public Iterable<Employee> getAllEmployees() {
    return leaveService.findEmployees();
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/leave")
  public EmployeeLeave addEmployeeLeaves(@RequestParam Long employeeId, @RequestParam Long year, @RequestParam Double leaveCount, @RequestParam Long leaveTypeId) {
    return leaveService.addEmployeeLeaves(employeeId, year, leaveCount, leaveTypeId);
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/leave/application")
  public void addAppliedLeave(@RequestParam Long employeeId, @RequestParam Date leaveFrom, @RequestParam Date leaveTo, @RequestParam Long leaveTypeId, @RequestParam String commentByApplicant,
                              @RequestParam Long appliedFor) throws InsufficientLeavesException {
    leaveService.addAppliedLeave(employeeId, leaveFrom, leaveTo, leaveTypeId, commentByApplicant, appliedFor);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/leave/application")
  public Iterable<LeaveApplication> getAllAppliedLeaves(@RequestParam Long employeeId) {
    return leaveService.findAppliedLeaves(employeeId);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/leave/application")
  public void processAppliedLeave(@RequestParam(value = "managerId", required = false) Long managerId, @RequestParam Long leaveApplicationId, @RequestParam Long leaveStatusId,
                                  @RequestParam(value = "commentByManager", required = false) String commentByManager) {
    leaveService.processAppliedLeave(managerId, leaveApplicationId, leaveStatusId, commentByManager);
  }
}
