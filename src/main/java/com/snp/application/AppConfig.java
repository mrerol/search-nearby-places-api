package com.snp.application;

import java.util.ResourceBundle;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConfig {

  private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
  private static ResourceBundle resource;

  static {
    try {
      resource = ResourceBundle.getBundle("client");
    } catch (Exception e) {
      logger.error("Could not initialize " + AppConfig.class, e);
    }
  }

  public static String getValue(String key) {
    try {
      return resource.getString(key);
    } catch (Exception e) {
      logger.error("Could not get value for key: {}", key, e);
      return null;
    }
  }

  public static boolean containsKey(String key) {
    try {
      return resource.containsKey(key);
    } catch (Exception e) {
      logger.error("Could not get value for key: {}", key, e);
      return false;
    }
  }
}
