package edu.unc.comp301.a08adapter;

public class BuildingImpl implements Building {
  private final String name;
  private final String description;
  private Location loc;
  private final NominatimGeocoder geocoder =
      new NominatimGeocoder("UNCGeocoder (aluhadia@unc.edu)");

  public BuildingImpl(String name, String description) {
    this.name = name;
    this.description = description;
    String address = name + ", Chapel Hill, North Carolina";
    try {
      this.loc = geocoder.geocode(address);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public double getLatitude() {
    return loc.getLatitude();
  }

  @Override
  public double getLongitude() {
    return loc.getLongitude();
  }
}
