package com.snp.rest.services;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.MongoCollection;
import com.snp.external.client.RestClientExecutor;
import com.snp.external.database.MongoDBClient;
import com.snp.external.types.DisplayName;
import com.snp.external.types.Place;
import com.snp.external.types.SearchNearbyResponse;
import java.util.List;
import java.util.Map;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SearchNearbyPlacesService {

  private static final Logger logger = LoggerFactory.getLogger(SearchNearbyPlacesService.class);

  public SearchNearbyResponse callSearchNearby(Map<String, String> params) {
    try {
      String key = constructKey(params);
      Document document = getFromDatabase(key);
      if (document == null) {
        // go to api
        RestClientExecutor restClientExecutor = new RestClientExecutor(params);
        SearchNearbyResponse response = restClientExecutor.call();
        addDatabase(response, key);
        return response;
      } else {
        List<Document> places = (List<Document>) document.get("places");
        List<Place> placeList = places.stream().map(this::convertPlace).toList();
        SearchNearbyResponse response = new SearchNearbyResponse();
        response.setPlaces(placeList);
        return response;
      }
    } catch (Exception e) {
      logger.error("Error callSearchNearby: ", e);
      return null;
    }
  }

  private String constructKey(Map<String, String> params) {
    return params.get("latitude")
        + params.get("longitude")
        + params.get("radius")
        + params.get("maxResultCount");
  }

  private Document getFromDatabase(String key) {
    try {
      MongoCollection<Document> mongoCollection = MongoDBClient.newConnection();
      if (mongoCollection == null) {
        logger.error("Could not connect to database");
        return null;
      }
      return mongoCollection.find(eq("_id", key)).first();
    } catch (Exception e) {
      logger.error("Error getFromDatabase: ", e);
      return null;
    }
  }

  private void addDatabase(SearchNearbyResponse response, String key) {
    try {
      MongoCollection<Document> mongoCollection = MongoDBClient.newConnection();
      if (mongoCollection == null) {
        logger.error("Could not connect to database");
        return;
      }
      mongoCollection.insertOne(createDocument(response.getPlaces(), key));
    } catch (Exception e) {
      logger.error("Error addDatabase: ", e);
    }
  }

  private Document createDocument(List<Place> places, String key) {
    Document document = new Document();
    document.append("_id", key);
    document.append("places", createPlaces(places));
    return document;
  }

  private List<Document> createPlaces(List<Place> places) {
    return places.stream().map(this::createPlaceDocument).toList();
  }

  private Document createPlaceDocument(Place place) {
    Document document = new Document();
    document.append("displayName", place.getDisplayName().getText());
    document.append("displayNameLang", place.getDisplayName().getLanguageCode());
    return document;
  }

  private Place convertPlace(Document document) {
    Place place = new Place();
    place.setDisplayName(createDisplayName(document));
    return place;
  }

  private DisplayName createDisplayName(Document document) {
    DisplayName displayName = new DisplayName();
    displayName.setText(document.getString("displayName"));
    displayName.setLanguageCode(document.getString("displayNameLang"));
    return displayName;
  }
}
