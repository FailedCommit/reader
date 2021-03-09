package com.maat.configservice;

import com.maat.configservice.config.ConfigProperties;
import com.maat.configservice.config.RestrictedApiConfig;
import com.maat.configservice.config.UrestrictedApiConfig;
import com.maat.configservice.config.UserApiConfig;
import com.maat.configservice.repo.HostConfigRepo;
import com.maat.configservice.repo.HostConfigRepoMongoImpl;
import com.maat.configservice.repo.ServerConfigRepo;
import com.maat.configservice.repo.ServerConfigRepoMongoImpl;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import static java.lang.System.getenv;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

@SpringBootApplication
@Configuration
@EnableAsync
public class ConfigServiceLauncher {

  public static final String APP_ROOT = "/config";

  public static void main(String[] args) {
    SpringApplication.run(ConfigServiceLauncher.class, args);
  }

  @Bean
  public ServletRegistrationBean userApi() {
    DispatcherServlet dispatcherServlet = new DispatcherServlet();
    AnnotationConfigWebApplicationContext applicationContext =
        new AnnotationConfigWebApplicationContext();
    applicationContext.register(UserApiConfig.class);
    dispatcherServlet.setApplicationContext(applicationContext);
    ServletRegistrationBean servletRegistrationBean =
        new ServletRegistrationBean<>(dispatcherServlet, APP_ROOT + "/u/*");
    servletRegistrationBean.setName("ConfigReadApi");
    servletRegistrationBean.setLoadOnStartup(1);
    return servletRegistrationBean;
  }

  @Bean
  public ServletRegistrationBean unrestrictedApi() {
    DispatcherServlet dispatcherServlet = new DispatcherServlet();
    AnnotationConfigWebApplicationContext applicationContext =
        new AnnotationConfigWebApplicationContext();
    applicationContext.register(UrestrictedApiConfig.class);
    dispatcherServlet.setApplicationContext(applicationContext);
    ServletRegistrationBean servletRegistrationBean =
        new ServletRegistrationBean<>(dispatcherServlet, APP_ROOT + "/p/*");
    servletRegistrationBean.setName("HealthApi");
    servletRegistrationBean.setLoadOnStartup(1);
    return servletRegistrationBean;
  }

  @Bean
  public ServletRegistrationBean restrictedApi() {
    DispatcherServlet dispatcherServlet = new DispatcherServlet();
    AnnotationConfigWebApplicationContext applicationContext =
        new AnnotationConfigWebApplicationContext();
    applicationContext.register(RestrictedApiConfig.class);
    dispatcherServlet.setApplicationContext(applicationContext);
    ServletRegistrationBean servletRegistrationBean =
        new ServletRegistrationBean<>(dispatcherServlet, APP_ROOT + "/restricted/*");
    servletRegistrationBean.setName("ConfigWriteApi");
    servletRegistrationBean.setLoadOnStartup(1);
    return servletRegistrationBean;
  }

  @Bean
  public String getAppRoot() {
    return APP_ROOT;
  }

  //  @Bean
  //  public ServletRegistrationBean healthApi() {
  //    return createDefaultHelthApi(getAppRoot());
  //  }

  @Bean(name = "asyncThreadPool")
  // TODO: Create general thread pool properties. Don't hardcode.
  public Executor asyncThreadPool() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(10);
    executor.setMaxPoolSize(200);
    executor.setThreadNamePrefix("asyncThreadPool-");
    executor.setQueueCapacity(400);
    executor.initialize();
    return executor;
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public HostConfigRepo getHostConfigRepo() {
    String config_type = getenv("config_type");
    // Allows to chose dbs in runtime
    if ("mongo".equals(config_type)) {
      return new HostConfigRepoMongoImpl(globalMongoTemplate());
    }
    return new HostConfigRepoMongoImpl(globalMongoTemplate());
  }

  @Bean
  public ServerConfigRepo getServerConfigRepo() {
    String config_type = getenv("config_type");
    if ("mongo".equals(config_type)) {
      return new ServerConfigRepoMongoImpl(globalMongoTemplate());
    }
    // In case, mongo is not the default anymore
    return new ServerConfigRepoMongoImpl(globalMongoTemplate());
  }

  public MongoTemplate globalMongoTemplate() {
    if (!"local".equalsIgnoreCase(getenv("CLUSTER_TYPE"))) {
      throw new UnsupportedOperationException("Cannot use mongo for anything except local");
    }
    String hostPortsCsv = ConfigProperties.getInstance().getGlobalMongoHostPortCsv();
    String dbName = ConfigProperties.getInstance().getGlobalMongoDBName();
    List<ServerAddress> serverAddresses = createServerAddresses(hostPortsCsv);
    // this code runs only on local machines
    //TODO: uncomment and fix below mongo config
    MongoClient mongoClient = new MongoClient(serverAddresses, getMongoOptions());
    return new MongoTemplate(mongoClient, dbName);
  }

  private List<ServerAddress> createServerAddresses(String hostPortsCsv) {
    List<String> hostPorts = breakString(hostPortsCsv, ",");
    List<ServerAddress> addresses = new ArrayList<>();
    for (String hostPort : hostPorts) {
      List<String> hostDetails = breakString(hostPort, ":");
      if (hostDetails.size() != 2) {
        throw new RuntimeException("Failed to parse global db host");
      }
      addresses.add(new ServerAddress(hostDetails.get(0), Integer.valueOf(hostDetails.get(1))));
    }
    return addresses;
  }

  private static MongoClientOptions getMongoOptions() {
    ConfigProperties properties = ConfigProperties.getInstance();
    String autoConnectRetry = properties.getProperty ( "mongo.autoConnectRetry", "true" );
    String socketKeepAlive = properties.getProperty ( "mongo.socketKeepAlive", "true" );
    String connectionsPerHost = properties.getProperty ( "mongo.connectionsPerHost", "50" );
    String threadsAllowedToBlockForConnectionMultiplier =
            properties.getProperty ( "mongo.threadsAllowedToBlockForConnectionMultiplier", "300" );
    MongoClientOptions.Builder builder = new MongoClientOptions.Builder ( );
    builder.connectTimeout ( (int) TimeUnit.SECONDS.toMillis ( 60 ) );
    builder.threadsAllowedToBlockForConnectionMultiplier ( Integer.valueOf ( threadsAllowedToBlockForConnectionMultiplier ) );
    builder.connectionsPerHost ( Integer.valueOf ( connectionsPerHost ) );
    return builder.build ( );
  }

  private static List<String> breakString(String s, String delimeter) {
    if (s == null || s.isEmpty()) return Collections.emptyList();
    String[] tokens = s.split(delimeter);
    List<String> tokenList = new ArrayList<>();
    for (String token : tokens) {
      String cleanToken = token.trim();
      if (isNotBlank(cleanToken)) {
        tokenList.add(cleanToken);
      }
    }
    return tokenList;
  }
}
