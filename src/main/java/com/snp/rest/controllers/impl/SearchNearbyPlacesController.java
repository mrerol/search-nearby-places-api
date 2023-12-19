package com.snp.rest.controllers.impl;

import com.snp.rest.controllers.interfaces.SearchNearbyPlacesControllerInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchNearbyPlacesController implements SearchNearbyPlacesControllerInterface {

  @GetMapping("/places")
  @Override
  public ResponseEntity<String> nearbySearch() {
    return new ResponseEntity<>("API is up and running", HttpStatus.OK);
  }
}
