package io.jing.server.user.config;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.jing.server.user.constant.UserConstant;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * @author jingshouyan
 * @date 2018/4/24 21:33
 */
@Configuration
@Slf4j
public class ServerConfig {
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        DataSource dataSource = dataSource();
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    DataSource dataSource(){
        return initDataSource();
    }

    private DataSource initDataSource(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(UserConstant.DS_URL);
        config.setDriverClassName(UserConstant.DS_DRIVER);
        config.setUsername(UserConstant.DS_USERNAME);
        config.setPassword(UserConstant.DS_PASSWORD);
        config.setMaximumPoolSize(20);
        config.setMinimumIdle(5);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

}
