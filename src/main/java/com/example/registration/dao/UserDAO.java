package com.example.registration.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {

	private final JdbcTemplate jdbc;

	public UserDAO(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public boolean authenticate(String username, String password) {
		String sql = "SELECT COUNT(*) FROM `user` WHERE username = ? AND password = ?";
		Integer count = jdbc.queryForObject(sql, Integer.class, username, password);
		return count != null && count == 1;
	}
}
