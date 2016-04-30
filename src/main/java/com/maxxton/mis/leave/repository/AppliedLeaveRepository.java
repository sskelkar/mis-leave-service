package com.maxxton.mis.leave.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


import com.maxxton.mis.leave.domain.AppliedLeave;
import com.maxxton.mis.leave.domain.EmployeeLeave;

public interface AppliedLeaveRepository extends JpaRepository<AppliedLeave, Long>, JpaSpecificationExecutor<AppliedLeave> {
  List<AppliedLeave> findByEmployeeId(Long employeeId);

  List<AppliedLeave> findByEmployeeIdAndLeaveStatusNameIgnoreCase(Long employeeId, String leaveStatus);

  /*
   *  Following JPQL is equivalent to:
   * 
   *  SELECT al.* 
   *  FROM applied_leave al
   *  JOIN leave_status ls ON (ls.leave_status_id = al.leave_status_id)
   *  WHERE al.employee_id = {employeeId}
   *  AND ls.name IN ('Pending', 'Approved')
   *  AND 
   *     (({leaveTo} > al.leave_from AND {leaveFrom} < al.leave_to) 
   *      OR ({leaveTo} = al.leave_from AND NOT (al.leave_from_half = 'Second' AND {leaveToHalf} = 'First'))
   *      OR ({leaveFrom} = al.leave_to AND NOT (al.leave_to_half = 'First' AND {leaveFromHalf} = 'Second'))
   *     );
   *     
   *  The query returns existing leaves that are overlapping with the duration of the leave we are trying to apply. Overlap exists if any of the following is true:
   *  1. any portion of new leave's period falls inside the period of any existing leave.
   *  2. if we are applying for a leave that ends on the same date as the start date of any existing leave, they won't overlap if new leave ends in first half and existing leaves starts from second half.
   *  3. if we are applying for a leave that starts on the same date as the end date of any existing leave, they won't overlap if the existing leave ends in the first half and the new leave starts from second half.     
   */
  @Query("select l from AppliedLeave l inner join l.leaveStatus ls "
      + "where l.employeeId = ?1 and ls.name in ('Pending', 'Approved') "
      + "and ?3 like 'First' and ?5 like 'Second' "      
      + "and ((?4 > l.leaveFrom and ?2 < TRUNC(l.leaveTo)) "
      + "or (?4 = l.leaveFrom and not (l.leaveFromHalf like 'Second' and ?5 like 'First')) "
      + "or (?2 = l.leaveTo and not (l.leaveToHalf like 'First' and ?3 like 'Second'))) "
)
  List<AppliedLeave> findOverlappingLeaves(Long employeeId, Date leaveFrom, String leaveFromHalf, Date leaveTo, String leaveToHalf);
  
>>>>>>> develop
}
