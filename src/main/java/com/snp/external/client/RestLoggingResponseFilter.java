package com.snp.external.client;

import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientResponseContext;
import jakarta.ws.rs.client.ClientResponseFilter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestLoggingResponseFilter implements ClientResponseFilter {

  private static final Logger logger = LoggerFactory.getLogger(RestLoggingResponseFilter.class);

  @Override
  public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) {
    if (!responseContext.hasEntity()) {
      return;
    }
    responseContext.setEntityStream(createResponseEntity(responseContext, requestContext));
  }

  private InputStream createResponseEntity(
      ClientResponseContext responseContext, ClientRequestContext requestContext) {
    try {
      InputStream stream = responseContext.getEntityStream();
      if (hasGZIP(requestContext, responseContext)) {
        stream = new GZIPInputStream(responseContext.getEntityStream());
      }
      ByteArrayOutputStream buf = readOutputStream(stream);
      String responseString = buf.toString().replaceAll("[\r\n]+", " ");
      String log =
          String.format(
              "Response:%n" + "Endpoint: %s%n" + "Body: %s",
              requestContext.getUri(), responseString);
      logger.info(log);
      return new ByteArrayInputStream(responseString.getBytes());
    } catch (Exception e) {
      logger.error("Error on " + RestLoggingResponseFilter.class, e);
      return new ByteArrayInputStream("".getBytes());
    }
  }

  private ByteArrayOutputStream readOutputStream(InputStream stream) throws IOException {
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    int b;
    while ((b = stream.read()) != -1) {
      buf.write((byte) b);
    }
    return buf;
  }

  private boolean hasGZIP(
      ClientRequestContext requestContext, ClientResponseContext responseContext) {
    return requestContext.getHeaders().containsKey("Accept-Encoding")
        && requestContext.getHeaders().get("Accept-Encoding").get(0).toString().contains("gzip")
        && responseContext.getHeaders().containsKey("Content-Encoding")
        && responseContext.getHeaders().get("Content-Encoding").get(0).contains("gzip");
  }
}
