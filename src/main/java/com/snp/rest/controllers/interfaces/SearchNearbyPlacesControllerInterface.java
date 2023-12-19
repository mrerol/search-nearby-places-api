package com.snp.rest.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface SearchNearbyPlacesControllerInterface {

  ResponseEntity<String> nearbySearch();
}
