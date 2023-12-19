package com.snp.rest.controllers.impl;

import com.snp.rest.controllers.interfaces.SearchNearbyPlacesControllerInterface;
import com.snp.rest.services.SearchNearbyPlacesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchNearbyPlacesController implements SearchNearbyPlacesControllerInterface {

  private final SearchNearbyPlacesService snpService;

  public SearchNearbyPlacesController(SearchNearbyPlacesService snpService) {
    this.snpService = snpService;
  }

  @GetMapping(value = "/places")
  @Override
  public ResponseEntity<String> nearbySearch() {
    return new ResponseEntity<>(snpService.callSearchNearby(), HttpStatus.OK);
  }
}
