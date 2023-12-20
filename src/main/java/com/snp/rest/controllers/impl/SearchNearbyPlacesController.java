package com.snp.rest.controllers.impl;

import com.snp.external.types.SearchNearbyResponse;
import com.snp.rest.controllers.interfaces.SearchNearbyPlacesControllerInterface;
import com.snp.rest.services.SearchNearbyPlacesService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class SearchNearbyPlacesController implements SearchNearbyPlacesControllerInterface {

  private static final Logger logger = LoggerFactory.getLogger(SearchNearbyPlacesController.class);

  private final SearchNearbyPlacesService snpService;

  public SearchNearbyPlacesController(SearchNearbyPlacesService snpService) {
    this.snpService = snpService;
  }

  @GetMapping(value = "/places")
  @Override
  public ResponseEntity<SearchNearbyResponse> nearbySearch(Map<String, String> params) {
    try {
      return new ResponseEntity<>(snpService.callSearchNearby(params), HttpStatus.OK);
    } catch (Exception e) {
      logger.error("Error: " + SearchNearbyPlacesController.class, e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
