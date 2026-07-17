package com.example.registration.online_registration;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

@Component
public class DBConfig {

	// Initialize Dotenv gracefully. 'ignoreIfMissing()' allows it to fall back to system variables on a live server.
	private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

	/**
	 * Retrieves the database user securely from the .env file.
	 * @return The database username
	 */
	public static String getDatabaseUser() {
		String user = dotenv.get("DB_USER");

		// Gracefully handle missing configuration
		if (user == null || user.trim().isEmpty()) {
		System.err.println("Configuration Error: The 'DB_USER' variable is missing from the .env file.");
		throw new IllegalStateException("Application cannot start: Database user not configured.");
		}

		return user;
	}

	/**
	 * Retrieves the database password securely from the .env file.
	 * @return The database password
	 */
	public static String getDatabasePassword() {
		String password = dotenv.get("DB_PASSWORD");

		// Gracefully handle missing configuration
		if (password == null || password.trim().isEmpty()) {
		System.err.println("Configuration Error: The 'DB_PASSWORD' variable is missing from the .env file. Please verify your .env file is set up correctly.");
		throw new IllegalStateException("Application cannot start: Database password not configured.");
		}

		return password;
	}
}