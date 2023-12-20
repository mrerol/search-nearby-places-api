package com.snp.external.client;

import com.snp.application.AppConfig;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RestClientPortFactory {

  private static final Logger logger = LoggerFactory.getLogger(RestClientPortFactory.class);
  private static String API_KEY;
  private static String ENDPOINT;

  private static Client client;

  static {
    try {
      API_KEY = AppConfig.getClientValue("API_KEY");
      ENDPOINT = AppConfig.getClientValue("API_ENDPOINT");
      client =
          ClientBuilder.newClient()
              .register(new RestLoggingRequestFilter())
              .register(new RestLoggingResponseFilter());
    } catch (Exception e) {
      logger.error("Could not initialize " + RestClientPortFactory.class, e);
    }
  }

  public static Invocation.Builder newPort() {
    Invocation.Builder builder = null;
    try {
      List<String> fieldMask = new ArrayList<>(Collections.singleton("places.displayName"));
      builder = newPortInstance(fieldMask);
    } catch (Exception e) {
      logger.error("Could not instantiate singleton " + RestClientPortFactory.class, e);
    }
    return builder;
  }

  private static Invocation.Builder newPortInstance(List<String> fieldMask) {
    return client
        .target(ENDPOINT)
        .request(MediaType.APPLICATION_JSON_TYPE)
        .header("Accept", MediaType.APPLICATION_JSON_TYPE)
        .header("Content-Type", MediaType.APPLICATION_JSON_TYPE)
        .header("X-Goog-Api-Key", API_KEY)
        .header("X-Goog-FieldMask", String.join(",", fieldMask));
  }
}
