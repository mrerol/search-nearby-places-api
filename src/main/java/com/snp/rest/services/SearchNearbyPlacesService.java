package com.snp.rest.services;

import com.snp.external.client.RestClientExecutor;
import com.snp.external.types.SearchNearbyResponse;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SearchNearbyPlacesService {

  private static final Logger logger = LoggerFactory.getLogger(SearchNearbyPlacesService.class);

  public SearchNearbyResponse callSearchNearby(Map<String, String> params) {
    try {
      RestClientExecutor restClientExecutor = new RestClientExecutor(params);
      return restClientExecutor.call();
    } catch (Exception e) {
      logger.error("Error: " + SearchNearbyPlacesService.class, e);
      return null;
    }
  }
}
