package com.snp.application;

import java.util.ResourceBundle;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConfig {

  private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
  private static ResourceBundle clientResource;
  private static ResourceBundle appResource;

  static {
    try {
      clientResource = ResourceBundle.getBundle("client");
      appResource = ResourceBundle.getBundle("application");
    } catch (Exception e) {
      logger.error("Could not initialize " + AppConfig.class, e);
    }
  }

  public static String getClientValue(String key) {
    try {
      return clientResource.getString(key);
    } catch (Exception e) {
      logger.error("Could not get value for key: {}", key, e);
      return null;
    }
  }

  public static boolean clientContainsKey(String key) {
    try {
      return clientResource.containsKey(key);
    } catch (Exception e) {
      logger.error("Could not get value for key: {}", key, e);
      return false;
    }
  }

  public static String getAppValue(String key) {
    try {
      return appResource.getString(key);
    } catch (Exception e) {
      logger.error("Could not get value for key: {}", key, e);
      return null;
    }
  }

  public static boolean appContainsKey(String key) {
    try {
      return appResource.containsKey(key);
    } catch (Exception e) {
      logger.error("Could not get value for key: {}", key, e);
      return false;
    }
  }
}
