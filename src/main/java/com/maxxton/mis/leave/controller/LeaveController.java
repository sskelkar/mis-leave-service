package com.maxxton.mis.leave.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maxxton.mis.leave.domain.Employee;
import com.maxxton.mis.leave.repository.EmployeeRepository;

@Controller
@RequestMapping(value = "/leave")
public class LeaveController
{
  @Autowired
  private EmployeeRepository employeeRepository;
  
  @ResponseBody
  @RequestMapping(method = RequestMethod.GET, value = "/employee")
  public Iterable<Employee> findEmployees()
  {
    return employeeRepository.findAll();
  }
}
