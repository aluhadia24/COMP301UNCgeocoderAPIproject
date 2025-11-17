package edu.unc.comp301.a08adapter;

import java.util.*;

public class UNCBuildingApiImpl implements UNCBuildingApi {
  private final Map<String, Building> buildings = new HashMap<>();

  @Override
  public Building getBuilding(String name) {
    return buildings.get(name);
  }

  @Override
  public List<String> getAllBuildingNames() {
    Collection<Building> buildingCollection = buildings.values();
    List<String> buildingNames = new ArrayList<>();
    for (Building building : buildingCollection) {
      buildingNames.add(building.getName());
    }
    return buildingNames;
  }

  @Override
  public void getDirections(String startBuilding, String endBuilding) {}

  public void add(String name, Building description) {
    buildings.put(name, description);
  }
}
