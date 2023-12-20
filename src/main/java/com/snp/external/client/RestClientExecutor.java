package com.snp.external.client;

import com.snp.external.types.*;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestClientExecutor {

  private static final Logger logger = LoggerFactory.getLogger(RestClientExecutor.class);
  private final String latitude;
  private final String longitude;
  private final String radius;
  private final String maxResultCount;

  public RestClientExecutor(Map<String, String> params) {
    this.latitude = params.get("latitude");
    this.longitude = params.get("longitude");
    this.radius = params.get("radius");
    this.maxResultCount = params.get("maxResultCount");
  }

  public SearchNearbyResponse call() {
    try {
      Invocation.Builder client = RestClientPortFactory.newPort();
      SearchNearbyRequest requestBody = createRequest();
      Response response = client.post(Entity.json(requestBody));
      SearchNearbyResponse snResponse = response.readEntity(SearchNearbyResponse.class);
      response.close();
      return snResponse;
    } catch (Exception e) {
      logger.error("Error: " + RestClientExecutor.class, e);
      return null;
    }
  }

  private SearchNearbyRequest createRequest() {
    SearchNearbyRequest requestBody = new SearchNearbyRequest();
    requestBody.setMaxResultCount(Integer.parseInt(this.maxResultCount));
    requestBody.setLocationRestriction(createLocationRestriction());
    return requestBody;
  }

  private LocationRestriction createLocationRestriction() {
    LocationRestriction locationRestriction = new LocationRestriction();
    locationRestriction.setCircle(createCircle());
    return locationRestriction;
  }

  private Circle createCircle() {
    Circle circle = new Circle();
    circle.setCenter(createCenter());
    circle.setRadius(Double.parseDouble(this.radius));
    return circle;
  }

  private Center createCenter() {
    Center center = new Center();
    center.setLatitude(Double.parseDouble(this.latitude));
    center.setLongitude(Double.parseDouble(this.longitude));
    return center;
  }

  private List<String> createIncludedTypes() {
    List<String> includedTypes = List.of("restaurant");
    return includedTypes;
  }
}
