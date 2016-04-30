package com.maxxton.mis.leave.exception;

public class InsufficientLeavesException extends RuntimeException {
  private static final long serialVersionUID = 2114634592165429401L;

  public InsufficientLeavesException() {
    this("This employee does not have sufficient leaves of this type.");
  }

  public InsufficientLeavesException(String message, Throwable cause) {
    super(message, cause);
  }

  public InsufficientLeavesException(String message) {
    super(message);
  }
}
