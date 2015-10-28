package com.maxxton.mis.leave.controller;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maxxton.mis.leave.domain.Employee;
import com.maxxton.mis.leave.domain.EmployeeLeave;
import com.maxxton.mis.leave.service.LeaveService;

@Controller
@RequestMapping(value = "/mis")
public class LeaveController
{
  @Autowired
  LeaveService leaveService;
  
  @ResponseBody
  @RequestMapping(method = RequestMethod.GET, value = "/employee")
  public Iterable<Employee> findEmployees()
  {
    return leaveService.findEmployees();
  }
  
  @ResponseBody
  @RequestMapping(method=RequestMethod.PUT, value="/leave")
  public EmployeeLeave addEmployeeLeaves(@RequestParam Long employeeId, @RequestParam Long year, @RequestParam Double leaveCount, @RequestParam Long leaveTypeId)
  {      
    return leaveService.addEmployeeLeaves(employeeId, year, leaveCount, leaveTypeId);
  }
  
  @ResponseBody
  @RequestMapping(method=RequestMethod.PUT, value="/leave/application")
  public void addAppliedLeave(@RequestParam Long employeeId, @RequestParam DateTime leaveFrom, @RequestParam DateTime leaveTo, @RequestParam Long leaveTypeId, 
      @RequestParam String commentByApplicant, @RequestParam Long appliedFor)
  {
    leaveService.addAppliedLeave(employeeId, leaveFrom, leaveTo, leaveTypeId, commentByApplicant, appliedFor);
  }
}
