package com.maxxton.mis.leave.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maxxton.mis.leave.domain.PublicHoliday;

public interface PublicHolidayRepository extends JpaRepository<PublicHoliday, Long> {

  PublicHoliday findByHolidayDate(Date holidayDate);
}
