package com.maxxton.mis.leave.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maxxton.mis.leave.domain.Employee;
import com.maxxton.mis.leave.domain.LeaveApplication;
import com.maxxton.mis.leave.exception.InsufficientLeavesException;
import com.maxxton.mis.leave.service.LeaveService;

@RestController
@RequestMapping(value = "/mis")
@Api(value = "mis-leave-api", description = "MIS Leave Service API")
public class LeaveController {

  @Autowired
  LeaveService leaveService;

  @RequestMapping(method = RequestMethod.GET, value = "/employee")
  @ApiOperation(value = "getAllEmployees", notes = "Retuns information of all employees.", response = Employee.class, responseContainer = "List")
  public Iterable<Employee> getAllEmployees() {
    return leaveService.getAllEmployees();
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/leave")
  @ApiOperation(value = "addEmployeeLeaves", notes = "Adds leaves of a given type and for the given year for an employee. Returns the employee leave id, if successful.", response = Long.class)
  public Long addEmployeeLeaves(@RequestParam Long employeeId, @RequestParam Long year, @RequestParam Double leaveCount, @RequestParam Long leaveTypeId) {
    return leaveService.addEmployeeLeaves(employeeId, year, leaveCount, leaveTypeId);
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/leave/application")
  @ApiOperation(value = "applyForLeave", notes = "Method to apply for leaves. Returns the leave application id, if successful. Throws InsufficientLeavesException.", response = Long.class)
  public Long applyForLeave(@RequestParam Long employeeId, @RequestParam Date leaveFrom, @RequestParam Date leaveTo, @RequestParam Long leaveTypeId, @RequestParam String commentByApplicant,
                            @RequestParam Long appliedFor) throws InsufficientLeavesException {
    return leaveService.applyForLeave(employeeId, leaveFrom, leaveTo, leaveTypeId, commentByApplicant, appliedFor);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/leave/application")
  @ApiOperation(value = "getAllAppliedLeaves", notes = "Retuns all applied leaves of an employee.", response = LeaveApplication.class, responseContainer = "List")
  public Iterable<LeaveApplication> getAllAppliedLeaves(@RequestParam Long employeeId) {
    return leaveService.getAllAppliedLeaves(employeeId);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/leave/application")
  @ApiOperation(value = "processAppliedLeave", notes = "Method to approve, reject or confirm an applied leave. Returns leave application id, if successful.", response = Long.class)
  public Long processAppliedLeave(@RequestParam(value = "managerId", required = false) Long managerId, @RequestParam Long leaveApplicationId, @RequestParam Long leaveStatusId,
                                  @RequestParam(value = "commentByManager", required = false) String commentByManager) {
    return leaveService.processAppliedLeave(managerId, leaveApplicationId, leaveStatusId, commentByManager);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/leave/application/pending")
  @ApiOperation(value = "getAllPendingLeaves", notes = "Retuns all leaves of an employee with status pending.", response = LeaveApplication.class, responseContainer = "List")
  public Iterable<LeaveApplication> getAllPendingLeaves(@RequestParam Long employeeId) {
    return leaveService.getAllPendingLeaves(employeeId);
  }
}
