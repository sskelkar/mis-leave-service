package com.maxxton.mis.leave.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee
{
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long employeeId;
  
  private String firstName;
  private String middleName;
  private String lastName;
  private String userName;
  private String password;
  private String email;
  private Date birthDate;
  private Date joiningDate;
  private String sex;
  private Double probationPeriodMonths;
  private Date releaseDate;
  private String panNumber;
  private String passportNumber;
  private Date passportValidTill;
  private String emergencyContactPerson;
  private String emergencyContactPhoneNumber;
  private String bloodGroup;
   
  public Long getEmployeeId()
  {
    return employeeId;
  }
  public void setEmployeeId(Long employeeId)
  {
    this.employeeId = employeeId;
  }
  public String getFirstName()
  {
    return firstName;
  }
  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }
  public String getMiddleName()
  {
    return middleName;
  }
  public void setMiddleName(String middleName)
  {
    this.middleName = middleName;
  }
  public String getLastName()
  {
    return lastName;
  }
  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }
  public String getUserName()
  {
    return userName;
  }
  public void setUserName(String userName)
  {
    this.userName = userName;
  }
  public String getPassword()
  {
    return password;
  }
  public void setPassword(String password)
  {
    this.password = password;
  }
  public String getEmail()
  {
    return email;
  }
  public void setEmail(String email)
  {
    this.email = email;
  }
  public Date getBirthDate()
  {
    return birthDate;
  }
  public void setBirthDate(Date birthDate)
  {
    this.birthDate = birthDate;
  }
  public Date getJoiningDate()
  {
    return joiningDate;
  }
  public void setJoiningDate(Date joiningDate)
  {
    this.joiningDate = joiningDate;
  }
  public String getSex()
  {
    return sex;
  }
  public void setSex(String sex)
  {
    this.sex = sex;
  }
  public Double getProbationPeriodMonths()
  {
    return probationPeriodMonths;
  }
  public void setProbationPeriodMonths(Double probationPeriodMonths)
  {
    this.probationPeriodMonths = probationPeriodMonths;
  }
  public Date getReleaseDate()
  {
    return releaseDate;
  }
  public void setReleaseDate(Date releaseDate)
  {
    this.releaseDate = releaseDate;
  }
  public String getPanNumber()
  {
    return panNumber;
  }
  public void setPanNumber(String panNumber)
  {
    this.panNumber = panNumber;
  }
  public String getPassportNumber()
  {
    return passportNumber;
  }
  public void setPassportNumber(String passportNumber)
  {
    this.passportNumber = passportNumber;
  }
  public Date getPassportValidTill()
  {
    return passportValidTill;
  }
  public void setPassportValidTill(Date passportValidTill)
  {
    this.passportValidTill = passportValidTill;
  }
  public String getEmergencyContactPerson()
  {
    return emergencyContactPerson;
  }
  public void setEmergencyContactPerson(String emergencyContactPerson)
  {
    this.emergencyContactPerson = emergencyContactPerson;
  }
  public String getEmergencyContactPhoneNumber()
  {
    return emergencyContactPhoneNumber;
  }
  public void setEmergencyContactPhoneNumber(String emergencyContactPhoneNumber)
  {
    this.emergencyContactPhoneNumber = emergencyContactPhoneNumber;
  }
  public String getBloodGroup()
  {
    return bloodGroup;
  }
  public void setBloodGroup(String bloodGroup)
  {
    this.bloodGroup = bloodGroup;
  }
}
