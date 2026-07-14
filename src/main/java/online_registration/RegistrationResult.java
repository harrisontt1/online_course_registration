package online_registration;

import java.time.LocalDateTime;

public class RegistrationResult {
	private final boolean success;
	private final String message;
	private final LocalDateTime timestamp;

	public RegistrationResult(boolean success, String message) {
		this.success = success;
		this.message = message;
		this.timestamp = LocalDateTime.now();
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	// Corresponds to +displayToStudent() from Slide 4
	public void displayToStudent() {
		if (success) {
		System.out.println("[SUCCESS] " + message + " (Processed at: " + timestamp + ")");
		} else {
		System.out.println("[REGISTRATION FAILED] " + message);
		}
	}
}