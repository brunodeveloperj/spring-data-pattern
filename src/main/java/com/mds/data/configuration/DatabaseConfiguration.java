package com.mds.data.configuration;

import static com.mds.data.helper.SecretExtractorHelper.extractSecretValue;

import com.mds.data.properties.DatabaseProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration class for reading secrets and connecting to the database.
 *
 * <p>Creates a HikariCP {@link DataSource}, an {@link EntityManagerFactory},
 * and a {@link PlatformTransactionManager} bean using values from
 * {@link DatabaseProperties}.
 *
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"${database.config.base}"})
public class DatabaseConfiguration {

  private final DatabaseProperties properties;

  public DatabaseConfiguration(DatabaseProperties properties) {
    this.properties = properties;
  }

  /**
   * Creates a DataSource Bean to connect to the database.
   *
   * @return Connection DataSource.
   */
  @Bean(name = "dataSource")
  @Primary
  public DataSource dataSource() {
    log.info("Initializing dataSource with URL: {}, poolName: {}, minPoolSize: {}, maxPoolSize: {}, schema: {}", properties.getUrl(), properties.getPoolName(), properties.getMinPoolSize(), properties.getMaxPoolSize(), properties.getSchema());
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(properties.getDriverClassName());
    hikariConfig.setJdbcUrl(properties.getUrl());
    hikariConfig.setUsername(extractSecretValue(properties.getUsername()));
    hikariConfig.setPassword(extractSecretValue(properties.getPassword()));
    hikariConfig.setPoolName(properties.getPoolName());
    hikariConfig.setMinimumIdle(properties.getMinPoolSize());
    hikariConfig.setMaximumPoolSize(properties.getMaxPoolSize());
    hikariConfig.setMaxLifetime(properties.getMaxLifetime());
    hikariConfig.setValidationTimeout(properties.getValidationTimeout());
    hikariConfig.setConnectionTimeout(properties.getConnectionTimeout());
    hikariConfig.setIdleTimeout(properties.getIdleTimeout());
    hikariConfig.setLeakDetectionThreshold(properties.getLeakDetectionThreshold());
    hikariConfig.setSchema(properties.getSchema());
    log.debug("HikariConfig: maxLifetime={}, validationTimeout={}, connectionTimeout={}, idleTimeout={}, leakDetectionThreshold={}", properties.getMaxLifetime(), properties.getValidationTimeout(), properties.getConnectionTimeout(), properties.getIdleTimeout(), properties.getLeakDetectionThreshold());
    return new HikariDataSource(hikariConfig);
  }

  @Bean(name = "entityManagerFactory")
  @Primary
  public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
    log.info("Initializing entityManagerFactory with packagesToScan: {}", Arrays.toString(properties.getPackagesToScan()));
    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    factory.setPackagesToScan(properties.getPackagesToScan());
    factory.setDataSource(dataSource);
    var defaultProperties = jpaProperties();
    factory.setJpaPropertyMap(defaultProperties);
    factory.afterPropertiesSet();
    log.debug("entityManagerFactory configured with JPA properties: {}", defaultProperties);
    return factory.getObject();
  }

  @Bean(name = "transactionManager")
  @Primary
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    log.info("Initializing transactionManager with EntityManagerFactory: {}", entityManagerFactory.getClass().getSimpleName());
    JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(entityManagerFactory);
    log.debug("transactionManager configured successfully.");
    return txManager;
  }

  private Map<String, Object> jpaProperties() {
    Map<String, Object> props = new HashMap<>();
    props.put("hibernate.physical_naming_strategy", CamelCaseToUnderscoresNamingStrategy.class.getName());
    props.put("hibernate.implicit_naming_strategy", ImplicitNamingStrategyJpaCompliantImpl.class.getName());
    props.put("hibernate.show_sql", properties.isShowSql());
    props.put("hibernate.format_sql", properties.isFormatSql());
    props.put("hibernate.hbm2ddl.auto", properties.getDdlAuto());
    return props;
  }

}
