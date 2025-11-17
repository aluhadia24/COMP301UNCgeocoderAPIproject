package edu.unc.comp301.a08adapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
  public static void main(String[] args) {
    // OpenRoutesService API Key
    Properties cfg = new Properties();
    try (InputStream in = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
      cfg.load(in);
    } catch (Exception e) {
      System.out.println(e);
    }
    String apiKey = cfg.getProperty("ors.api.key");

    UNCBuildingApiImpl unc = new UNCBuildingApiImpl();
    unc.add("sitterson hall", "computer science");
    unc.add("phillips hall", "math");
    unc.add("carroll hall", "media and journalism");

    try {
      WalkingDirectionsService service = new WalkingDirectionsService(apiKey);
      WalkingDirectionsServiceAdapter adapter = new WalkingDirectionsServiceAdapter(service, unc);
      adapter.getDirections("sitterson hall", "phillips hall");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
