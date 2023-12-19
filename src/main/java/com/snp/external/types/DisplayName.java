package com.snp.external.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class DisplayName {

  @JsonProperty("text")
  String text;

  @JsonProperty("languageCode")
  String languageCode;
}
