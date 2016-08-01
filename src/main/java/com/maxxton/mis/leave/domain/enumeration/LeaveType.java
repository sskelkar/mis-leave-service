package com.maxxton.mis.leave.domain.enumeration;

/**
 * Enum class for Leave type
 *
 * @author M. Gangurde (m.gangurde@maxxton.com)
 */
public enum LeaveType {
  CARRY_FORWARD ("Carry Forward"),
  COMPENSATORY_OFF ("Compensatory Off"),
  ENCASHED ("Encashed"),
  LEAVE_WITHOUT_PAY ("Leave Without Pay"),
  MATERNITY ("Maternity"),
  PATERNITY ("Paternity"),
  PLANNED ("Planned"),
  UNPLANNED ("Unplanned");

  private String value;

  LeaveType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static LeaveType getType(String value) {
    for (LeaveType type : LeaveType.values()) {
      if (type.getValue().equals(value)) {
        return type;
      }
    }
    return null;
  }
}
