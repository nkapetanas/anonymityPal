package com.research.privacy.anonymity.pal.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Create data source configuration class
 */

@Slf4j
@Configuration
public class DataSourceConfiguration {

    // any spring.datasource.presto-prefixed property will be mapped to the DataSource instance managed by the Spring context
    @Bean(name = "prestoDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.presto")
    public DataSource prestoDataSource() {
        log.info("-------------------- PrestoDB init ---------------------");
        return DataSourceBuilder.create().build();
    }

    @Bean
    public Jdbi jdbi(@Qualifier("prestoDataSource") DataSource dataSource) {
        return Jdbi.create(dataSource)
                .installPlugin(new SqlObjectPlugin());
    }
}
