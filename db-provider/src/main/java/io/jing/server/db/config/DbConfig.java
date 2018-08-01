package io.jing.server.db.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.jing.server.constant.ServerConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * @author jingshouyan
 * #date 2018/7/13 10:12
 */
@Configuration
@Slf4j
public class DbConfig {

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    DataSource dataSource(){
        return initDataSource();
    }

    private DataSource initDataSource(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(ServerConstant.DS_URL);
        config.setDriverClassName(ServerConstant.DS_DRIVER);
        config.setUsername(ServerConstant.DS_USERNAME);
        config.setPassword(ServerConstant.DS_PASSWORD);
        config.setMaximumPoolSize(20);
        config.setMinimumIdle(5);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }
}
