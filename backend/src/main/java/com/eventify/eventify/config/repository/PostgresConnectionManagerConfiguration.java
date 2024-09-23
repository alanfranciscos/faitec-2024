package com.eventify.eventify.config.repository;

import com.eventify.eventify.util.ResourceFileService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.sql.*;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class PostgresConnectionManagerConfiguration {
    // TODO VERIFY KEYS IN application.properties
    @Value("${spring.datasource.base.url}")
    private String databaseBaseUrl;
 
    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Value("${spring.datasource.username}")
    private String databaseUsername;

    @Value("${spring.datasource.password}")
    private String databasePassword;

    @Value("${spring.datasource.name}")
    private String databaseName;

    @Autowired
    private ResourceFileService resourceFileService;

    @Bean
    public DataSource dataSource() throws SQLException {
        final DataSource build = DataSourceBuilder
                .create()
                .url(databaseBaseUrl)
                .username(databaseUsername)
                .password(databasePassword)
                .build();
        final Connection connection = build.getConnection();
        createDatabaseIfNotExist(connection);
        return build;
    }

    private void createDatabaseIfNotExist(Connection connection) throws SQLException {
        final Statement statement = connection.createStatement();
        String sql = "SELECT COUNT(*) AS dbs ";
        sql += " FROM pg_catalog.pg_database ";
        sql += " WHERE lower(datname) = '"+ databaseName + "';";

        ResultSet resultSet = statement.executeQuery(sql);
        boolean dbExists = resultSet.next();
        if(!dbExists || resultSet.getInt("dbs") == 0) {
            String createDbSql = "CREATE DATABASE " + databaseName + " WITH ";
            createDbSql += " OWNER = postgres ENCODING = 'UTF-8' ";
            createDbSql += " CONNECTION LIMIT = 1 ";

            PreparedStatement preparedStatement = connection
                    .prepareStatement(createDbSql);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
    }


    @Bean
    @DependsOn("dataSource")
    public Connection getConnection() throws  SQLException {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(databaseUrl);
        hikariConfig.setUsername(databaseUsername);
        hikariConfig.setPassword(databasePassword);

        return new HikariDataSource(hikariConfig).getConnection();
    }


    @Bean
    @DependsOn("getConnection")
    public boolean createTableAndInsertData() throws IOException, SQLException {
            Connection connection = getConnection();

            final String basePath = "lds-db-scripts";
            final String createTable = resourceFileService
                    .read(basePath + "/create-tables-postgres.sql");

            PreparedStatement creaeStatement = connection
                    .prepareStatement(createTable);
            creaeStatement.executeUpdate();
            creaeStatement.close();

            final String insertData = resourceFileService
                    .read(basePath + "/insert-data.sql");
            PreparedStatement insertStatement = connection
                    .prepareStatement(insertData);
            insertStatement.execute();
            insertStatement.close();

            return true;
    }

}
