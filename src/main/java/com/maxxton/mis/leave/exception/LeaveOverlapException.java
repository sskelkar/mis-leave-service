package com.maxxton.mis.leave.exception;

public class LeaveOverlapException extends Exception {
  private static final long serialVersionUID = 2114634592165429401L;

  public LeaveOverlapException() {
    this("Leave period overlaps with existing leaves");
  }

  public LeaveOverlapException(String message, Throwable cause) {
    super(message, cause);
  }

  public LeaveOverlapException(String message) {
    super(message);
  }
}
