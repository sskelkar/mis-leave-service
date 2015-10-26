package com.maxxton.mis.leave.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PublicHoliday
{
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Number publicHolidayId;  
  private Date holidayDate;
  private String name;
  
  public Number getPublicHolidayId()
  {
    return publicHolidayId;
  }
  public void setPublicHolidayId(Number publicHolidayId)
  {
    this.publicHolidayId = publicHolidayId;
  }
  public Date getHolidayDate()
  {
    return holidayDate;
  }
  public void setHolidayDate(Date holidayDate)
  {
    this.holidayDate = holidayDate;
  }
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  
}
