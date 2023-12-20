package com.snp.external.database;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.snp.application.AppConfig;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MongoDBClient {
  private static final Logger logger = LoggerFactory.getLogger(MongoDBClient.class);
  private static MongoClient mongoClient;

  static {
    try {
      String dbUsername = AppConfig.getClientValue("DB_USERNAME");
      String dbPassword = AppConfig.getClientValue("DB_PASSWORD");
      String dbHost = AppConfig.getClientValue("DB_HOST");
      String connectionString =
          String.format(
              "mongodb+srv://%s:%s@%s/?retryWrites=true&w=majority",
              dbUsername, dbPassword, dbHost);
      ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
      MongoClientSettings settings =
          MongoClientSettings.builder()
              .applyConnectionString(new ConnectionString(connectionString))
              .serverApi(serverApi)
              .build();
      mongoClient = MongoClients.create(settings);
    } catch (Exception e) {
      logger.error("Could not initialize " + MongoDBClient.class, e);
    }
  }

  public static MongoCollection<Document> newConnection() {
    try {
      String dbName = AppConfig.getClientValue("DB_NAME");
      String collectionName = AppConfig.getClientValue("COLLECTION_NAME");
      if (dbName == null || collectionName == null) {
        logger.error("Could not find database or collection name!");
        return null;
      }
      MongoDatabase database = mongoClient.getDatabase(dbName);
      return database.getCollection(collectionName);
    } catch (Exception e) {
      logger.error("Unable to connect due to an error: ", e);
      return null;
    }
  }
}
