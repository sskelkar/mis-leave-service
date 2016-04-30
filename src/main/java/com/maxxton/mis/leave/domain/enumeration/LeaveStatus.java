package com.maxxton.mis.leave.domain.enumeration;

/**
 * Enum class for Leave status
 *
 * @author M. Gangurde (m.gangurde@maxxton.com)
 */
public enum LeaveStatus {
  APPROVED ("approved"),
  PENDING ("pending"),
  CANCELLED ("cancelled"),
  REJECTED ("rejected");

  private String value;

  LeaveStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static LeaveStatus getType(String value) {
    for (LeaveStatus status : LeaveStatus.values()) {
      if (status.getValue().equals(value)) {
        return status;
      }
    }
    return null;
  }
}
