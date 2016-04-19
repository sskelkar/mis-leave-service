package com.maxxton.mis.leave.domain;

public class AvailableLeaveCount {

  private Double planned;
  private Double unplanned;
  private Double compOff;
  
  public AvailableLeaveCount() {
    this.planned = 0.0;
    this.unplanned = 0.0;
    this.compOff = 0.0;
  }
  
  public Double getPlanned() {
    return planned;
  }
  public void setPlanned(Double planned) {
    this.planned = planned;
  }
  public Double getUnplanned() {
    return unplanned;
  }
  public void setUnplanned(Double unplanned) {
    this.unplanned = unplanned;
  }
  public Double getCompOff() {
    return compOff;
  }
  public void setCompOff(Double compOff) {
    this.compOff = compOff;
  }
  
  
}
