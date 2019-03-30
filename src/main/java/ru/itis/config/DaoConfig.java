package ru.itis.config;

import org.postgresql.Driver;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

public class DaoConfig {

    public static DataSource getDataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(Driver.class);
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/hack");
        return dataSource;
    }
}
