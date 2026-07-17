package com.example.registration.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EnrollmentController {

	@PostMapping("/enroll")
	public String enroll(@RequestBody EnrollmentRequest request) {
		return "Enrollment received: student " + request.studentId + ", section " + request.sectionId;
	}
}

class EnrollmentRequest {
	public int studentId;
	public int sectionId;
}
