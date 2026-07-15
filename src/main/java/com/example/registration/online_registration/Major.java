package com.example.registration.online_registration;

public class Major {
	private int majorId;
	private String majorCode;
	private String name;
	private int requiredCredits;

	public Major(String majorCode, String name, int requiredCredits) {
		this.majorCode = majorCode;
		this.name = name;
		this.requiredCredits = requiredCredits;
	}

	public Major(int majorId, String majorCode, String name, int requiredCredits) {
		this.majorId = majorId;
		this.majorCode = majorCode;
		this.name = name;
		this.requiredCredits = requiredCredits;
	}

	// Getters and Setters
	public int getMajorId() { return majorId; }
	public void setMajorId(int majorId) { this.majorId = majorId; }

	public String getMajorCode() { return majorCode; }
	public void setMajorCode(String majorCode) { this.majorCode = majorCode; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public int getRequiredCredits() { return requiredCredits; }
	public void setRequiredCredits(int requiredCredits) { this.requiredCredits = requiredCredits; }
}