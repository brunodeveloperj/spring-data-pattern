package com.mds.data;

import com.mds.data.properties.DatabaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring auto-configuration entry point for the MDS Data Pattern library.
 *
 * <p>Enables component scanning and binds {@link DatabaseProperties} from
 * the application configuration.
 *
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties(DatabaseProperties.class)
public class DataAutoConfiguration {

}
