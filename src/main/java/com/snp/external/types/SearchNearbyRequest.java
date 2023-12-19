package com.snp.external.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class SearchNearbyRequest {

  @JsonProperty("includedTypes")
  private List<String> includedTypes;

  @JsonProperty("maxResultCount")
  private Integer maxResultCount;

  @JsonProperty("locationRestriction")
  private LocationRestriction locationRestriction;
}
