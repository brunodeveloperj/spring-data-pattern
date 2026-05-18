package com.mds.data.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for database connectivity, bound from
 * the {@code database.config.properties} prefix in application configuration.
 *
 * <p>Includes JDBC URL, credentials, HikariCP pool settings, Hibernate
 * dialect options, and entity scan packages.
 *
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
@Data
@ConfigurationProperties(prefix = "database.config.properties")
public class DatabaseProperties {

  private String username;
  private String password;
  private String driverClassName;
  private String url;
  private String schema;
  private String poolName;
  private int minPoolSize;
  private int maxPoolSize;
  private long maxLifetime;
  private long validationTimeout;
  private long connectionTimeout;
  private long idleTimeout;
  private long leakDetectionThreshold;
  private boolean showSql = false;
  private boolean formatSql = true;
  private String ddlAuto = "none";
  private String[] packagesToScan;

}
