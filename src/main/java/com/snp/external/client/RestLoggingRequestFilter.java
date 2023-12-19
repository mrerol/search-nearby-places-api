package com.snp.external.client;

import com.snp.utils.CommonUtils;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestLoggingRequestFilter implements ClientRequestFilter {

  private static final Logger logger = LoggerFactory.getLogger(RestLoggingRequestFilter.class);

  @Override
  public void filter(ClientRequestContext requestContext) {
    try {
      String log =
          String.format(
              "Request:%n" + "Endpoint: %s%n" + "Body: %s",
              requestContext.getUri(), printEntity(requestContext));
      logger.info(log);
    } catch (Exception e) {
      String errorMessage = String.format("Error on %s", RestLoggingRequestFilter.class);
      logger.error(errorMessage, e);
    }
  }

  private String printEntity(ClientRequestContext requestContext) {
    try {
      if (!requestContext.hasEntity()) {
        return "";
      }
      if (requestContext.getHeaders().containsKey(HttpHeaders.CONTENT_TYPE)) {
        if (requestContext
            .getHeaders()
            .get(HttpHeaders.CONTENT_TYPE)
            .contains(MediaType.APPLICATION_JSON_TYPE)) {
          return CommonUtils.toJson(requestContext.getEntity());
        } else if (requestContext
            .getHeaders()
            .get(HttpHeaders.CONTENT_TYPE)
            .contains(MediaType.APPLICATION_XML_TYPE)) {
          return CommonUtils.marshalEntity(requestContext.getEntity());
        }
      }
      return "";
    } catch (Exception e) {
      String errorMessage = "Could not print entity!";
      logger.error(errorMessage, e);
      return "";
    }
  }
}
