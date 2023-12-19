package com.snp.rest.controllers.interfaces;

import com.snp.external.types.SearchNearbyResponse;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
public interface SearchNearbyPlacesControllerInterface {

  ResponseEntity<SearchNearbyResponse> nearbySearch(@RequestParam Map<String, String> params);
}
