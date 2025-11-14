package edu.unc.comp301.a08adapter;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class NominatimGeocoder {

  private final String baseURL = "https://nominatim.openstreetmap.org/search";
  private final OkHttpClient client = new OkHttpClient();
  private final String userAgent;

  public NominatimGeocoder(String userAgent) {
    this.userAgent = userAgent;
  }

  public String createURL(String address) {
    HttpUrl.Builder httpBuilder = HttpUrl.parse(baseURL).newBuilder();
    httpBuilder.addQueryParameter("q", address);
    httpBuilder.addQueryParameter("format", "json");
    httpBuilder.addQueryParameter("limit", "1");
    return httpBuilder.build().toString();
  }

  protected Request buildRequest(String url) {
    return new Request.Builder().url(url).header("User-Agent", userAgent).get().build();
  }

  protected String sendRequest(Request request) throws IOException {
    Response resp = client.newCall(request).execute();
    if (!resp.isSuccessful() || (resp.body() == null)) {
      throw new IOException();
    }
    return resp.body().string();
  }

  protected Location parseLocation(String json) throws IOException {
    JSONArray results = new JSONArray(json);
    if (results.isEmpty()) {
      return null;
    }
    JSONObject object = results.getJSONObject(0);
    double lat = object.getDouble("lat");
    double lon = object.getDouble("lon");
    Location location = new Location(lat, lon);
    return location;
  }

  public Location geocode(String address) throws IOException {
    return parseLocation(sendRequest(buildRequest(createURL(address))));
  }
}
