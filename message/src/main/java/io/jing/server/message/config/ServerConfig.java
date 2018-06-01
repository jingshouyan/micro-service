package io.jing.server.message.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import io.jing.server.message.constant.MessageConstant;

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
    public DataSource dataSource(){
        return initDataSource();
    }

    @Bean
    public SocketIOServer socketIOServer(){
        com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();
        configuration.setPort(MessageConstant.WS_PORT);
//        configuration.setHostname(MessageConstant.HOST_NAME);
        SocketIOServer socketIOServer = new SocketIOServer(configuration);
        return socketIOServer;
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer server){
        return new SpringAnnotationScanner(server);
    }

    private DataSource initDataSource(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(MessageConstant.DS_URL);
        config.setDriverClassName(MessageConstant.DS_DRIVER);
        config.setUsername(MessageConstant.DS_USERNAME);
        config.setPassword(MessageConstant.DS_PASSWORD);
        config.setMaximumPoolSize(20);
        config.setMinimumIdle(5);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

}
