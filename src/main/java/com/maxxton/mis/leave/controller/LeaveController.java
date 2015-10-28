package com.maxxton.mis.leave.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maxxton.mis.leave.domain.Employee;
import com.maxxton.mis.leave.domain.EmployeeLeave;
import com.maxxton.mis.leave.repository.EmployeeLeaveRepository;
import com.maxxton.mis.leave.repository.EmployeeRepository;

@Controller
@RequestMapping(value = "/mis")
public class LeaveController
{
  @Autowired
  private EmployeeRepository employeeRepository;
  
  @Autowired
  private EmployeeLeaveRepository employeeLeaveRepository;
  
  @ResponseBody
  @RequestMapping(method = RequestMethod.GET, value = "/employee")
  public Iterable<Employee> findEmployees()
  {
    return employeeRepository.findAll();
  }
  
  @ResponseBody
  @RequestMapping(method=RequestMethod.PUT, value="/leave")
  public EmployeeLeave addEmployeeLeaves(@RequestParam Long employeeId, @RequestParam Long year, @RequestParam Double leaveCount, @RequestParam Long leaveTypeId)
  {
    System.out.println("req received to save");
    EmployeeLeave employeeLeave = new EmployeeLeave();
    employeeLeave.setEmployeeId(employeeId);
    employeeLeave.setYear(year);
    employeeLeave.setLeaveCount(leaveCount);
    employeeLeave.setLeaveTypeId(leaveTypeId);
    System.out.println("saving");
    return employeeLeaveRepository.save(employeeLeave);
  }
}
