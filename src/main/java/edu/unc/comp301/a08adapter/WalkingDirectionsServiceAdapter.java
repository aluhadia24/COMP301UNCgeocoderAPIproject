package edu.unc.comp301.a08adapter;

import java.io.IOException;
import java.util.List;

public class WalkingDirectionsServiceAdapter implements UNCBuildingApi {

  private final WalkingDirectionsService service;
  private final UNCBuildingApiImpl api;

  public WalkingDirectionsServiceAdapter(WalkingDirectionsService service, UNCBuildingApiImpl api) {
    this.service = service;
    this.api = api;
  }

  @Override
  public Building getBuilding(String name) {
    return api.getBuilding(name);
  }

  @Override
  public List<String> getAllBuildingNames() {
    return api.getAllBuildingNames();
  }

  @Override
  public void getDirections(String startBuilding, String endBuilding) {
    Building start = api.getBuilding(startBuilding);
    if (start == null) {
      throw new IllegalArgumentException();
    }
    Building end = api.getBuilding(endBuilding);
    if (end == null) {
      throw new IllegalArgumentException();
    }
    double startLon = start.getLongitude();
    double startLat = start.getLatitude();
    double endLon = end.getLongitude();
    double endLat = end.getLatitude();
    try {
      service.getWalkingDirections(startLat, startLon, endLat, endLon);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
