package edu.unc.comp301.a08adapter;

import java.util.List;

public class DirectionsResult {
  private final List<Step> step;
  private final double totalDistance;
  private final double totalDuration;

  public DirectionsResult(List<Step> step, double totalDistance, double totalDuration) {
    this.step = step;
    this.totalDistance = totalDistance;
    this.totalDuration = totalDuration;
  }

  public List<Step> getStep() {
    return step;
  }

  public double getTotalDistance() {
    return totalDistance;
  }

  public double getTotalDuration() {
    return totalDuration;
  }
}
