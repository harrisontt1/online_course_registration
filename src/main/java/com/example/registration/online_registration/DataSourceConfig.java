package com.example.registration.online_registration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

	@Autowired
	private DBConfig dbConfig;

	@Bean
	public DataSource dataSource() {
		return DataSourceBuilder.create()
				.url("jdbc:mysql://localhost:3306/registrationdb")
				.username(dbConfig.getDatabaseUser())
				.password(dbConfig.getDatabasePassword())
				.driverClassName("com.mysql.cj.jdbc.Driver")
				.build();
	}
}
