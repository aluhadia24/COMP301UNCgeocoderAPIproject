package edu.unc.comp301.a08adapter;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WalkingDirectionsService {
  private final String baseURL = "https://api.openrouteservice.org/v2/directions/foot-walking";
  private final String apiKey;
  private final OkHttpClient client = new OkHttpClient();

  public WalkingDirectionsService(String apiKey) {
    this.apiKey = apiKey;
  }

  public String createURL(double start_lon, double start_lat, double end_lon, double end_lat) {
    HttpUrl.Builder httpBuilder = HttpUrl.parse(baseURL).newBuilder();
    String startPoint = start_lon + "," + start_lat;
    String endPoint = end_lon + "," + end_lat;
    httpBuilder.addQueryParameter("api_key", apiKey);
    httpBuilder.addQueryParameter("start", startPoint);
    httpBuilder.addQueryParameter("end", endPoint);
    httpBuilder.addQueryParameter("steps", "true");
    return httpBuilder.build().toString();
  }

  protected Request buildRequest(String url) {
    return new Request.Builder().url(url).get().build();
  }

  protected String sendRequest(Request request) throws IOException {
    Response resp = client.newCall(request).execute();
    if (!resp.isSuccessful() || (resp.body() == null)) {
      throw new IOException();
    }
    return resp.body().string();
  }

  protected DirectionsResult parseDirections(String json) {
    JSONObject obj = new JSONObject(json);
    JSONArray segments =
        obj.getJSONArray("features")
            .getJSONObject(0)
            .getJSONObject("properties")
            .getJSONArray("segments");
    List<Step> list = new ArrayList<>();
    double totalDistance = 0;
    double totalTime = 0;

    for (int i = 0; i < segments.length(); i++) {
      JSONObject segment = segments.getJSONObject(i);
      JSONArray steps = segment.getJSONArray("steps");
      for (int j = 0; j < steps.length(); j++) {
        JSONObject step = steps.getJSONObject(j);
        String instruction = step.getString("instruction");
        double distance = step.getDouble("distance");
        double duration = step.getDouble("duration");
        Step step1 = new Step(instruction, distance, duration);
        list.add(step1);
        totalDistance = totalDistance + distance;
        totalTime = totalTime + duration;
      }
    }
    return new DirectionsResult(list, totalDistance, totalTime);
  }

  public void getWalkingDirections(Location start, Location end) throws IOException {
    DirectionsResult result = parseDirections(sendRequest(buildRequest(createURL(start, end))));
    List<Step> steps = result.getSteps();
    for (int i = 0; i < steps.size(); i++) {
      System.out.println(
          (i + 1)
              + ". "
              + steps.get(i).getInstruction()
              + " - "
              + "("
              + steps.get(i).getDistance()
              + " meters, "
              + steps.get(i).getDuration()
              + " seconds)");
    }
    System.out.println("Total distance: " + result.getTotalDistance() + " m");
    System.out.println("Total time: " + result.getTotalDuration());
  }
}
