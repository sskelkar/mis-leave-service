package com.maxxton.mis.leave.domain.enumeration;

/**
 * Enum class for Leave type
 *
 * @author M. Gangurde (m.gangurde@maxxton.com)
 */
public enum LeaveType {
  CARRY_FORWARD ("carryforward"),
  COMPENSATORY_OFF ("compoff"),
  ENCASHED ("encashed"),
  LEAVE_WITHOUT_PAY ("lwp"),
  MATERNITY ("maternity"),
  PATERNITY ("paternity"),
  PLANNED ("planned"),
  UNPLANNED ("unplanned");

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
