package com.snp.external.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Circle {

  @JsonProperty("center")
  private Center center;

  @JsonProperty("radius")
  private Double radius;
}
