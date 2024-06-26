package com.zznode.dhmp.boot.autoconfigure.jdbc.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zznode.dhmp.boot.autoconfigure.jdbc.datasource.configurer.hikari.HikariDataSourceConfigurer;
import com.zznode.dhmp.jdbc.datasource.config.DynamicDataSourceConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 数据源配置类
 *
 * @author 王俊
 * @date create in 2023/6/2 9:52
 */
abstract class DynamicDataSourceConfiguration {

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(HikariDataSource.class)
    @ConditionalOnProperty(name = {"dhmp.datasource.type", "spring.datasource.type"}, havingValue = "com.zaxxer.hikari.HikariDataSource",
            matchIfMissing = true)
    static class Hikari {

        @Bean
        @ConfigurationProperties(prefix = "spring.datasource.hikari")
        HikariConfig hikariConfig() {
            return new HikariConfig();
        }

        @Bean
        @ConditionalOnSingleCandidate(HikariConfig.class)
        DynamicDataSourceConfigurer hikariDataSourceConfigurer(DynamicDataSourceProperties properties,
                                                               HikariConfig hikariConfig) {
            return new HikariDataSourceConfigurer(properties, hikariConfig);
        }
    }

    static class Dbcp2 {
        // 暂未支持
    }

    static class Druid {
        // 暂未支持
    }

}
