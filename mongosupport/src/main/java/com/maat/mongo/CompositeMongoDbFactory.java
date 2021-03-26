package com.maat.mongo;

import com.mongodb.ClientSessionOptions;
import com.mongodb.DB;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.mongodb.core.MongoDbFactorySupport;

public class CompositeMongoDbFactory extends MongoDbFactorySupport<MongoClient> {

  /**
   * Create a new {@link MongoDbFactorySupport} object given {@code mongoClient}, {@code
   * databaseName}, {@code mongoInstanceCreated} and {@link PersistenceExceptionTranslator}.
   *
   * @param mongoClient must not be {@literal null}.
   * @param mongoInstanceCreated {@literal true} if the client instance was created by a subclass of
   *     {@link MongoDbFactorySupport} to close the client on {@link #destroy()}.
   * @param exceptionTranslator must not be {@literal null}.
   */
  protected CompositeMongoDbFactory(
      MongoClient mongoClient,
      String defaultDbName,
      boolean mongoInstanceCreated,
      PersistenceExceptionTranslator exceptionTranslator) {
    super(mongoClient, defaultDbName, mongoInstanceCreated, exceptionTranslator);
  }

  /**
   * Get the actual {@link MongoDatabase} from the client.
   *
   * @param dbName must not be {@literal null} or empty.
   * @return
   */
  @Override
  protected MongoDatabase doGetMongoDatabase(String dbName) {
    return getMongoClient().getDatabase(dbName);
  }

  /** Close the client instance. */
  @Override
  protected void closeClient() {
    getMongoClient().close();
  }

  /**
   * Get the legacy database entry point. Please consider {@link #getDb()} instead.
   *
   * @return
   * @deprecated since 2.1, use {@link #getDb()}. This method will be removed with a future version
   *     as it works only with the legacy MongoDB driver.
   */
  @Override
  public DB getLegacyDb() {
    throw new UnsupportedOperationException("Can't allow legacy DB");
  }

  /**
   * Obtain a {@link ClientSession} for given ClientSessionOptions.
   *
   * @param options must not be {@literal null}.
   * @return never {@literal null}.
   * @since 2.1
   */
  @Override
  public ClientSession getSession(ClientSessionOptions options) {
      return getMongoClient().startSession(options);
  }
}
