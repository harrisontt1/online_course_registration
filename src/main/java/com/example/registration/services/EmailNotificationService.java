package com.example.registration.services;

import org.springframework.stereotype.Service;

/**
 * Service layer responsible for preparing student email notifications.
 *
 * <p>This implementation creates an email notification preview in the
 * backend console. It avoids requiring real SMTP usernames, passwords,
 * or Gmail app passwords during team development.</p>
 *
 * <p><strong>Notification System Component:</strong></p>
 * <ul>
 *     <li>Supports successful course registration notifications</li>
 *     <li>Supports failed course registration notifications</li>
 *     <li>Supports successful or failed course withdrawal notifications</li>
 *     <li>Can be upgraded later to send real SMTP email</li>
 * </ul>
 */
@Service
public class EmailNotificationService {

    /**
     * Sends a registration-related notification to the student.
     *
     * <p>For this project version, the email is shown as a console
     * preview instead of being sent through an outside email server.
     * This keeps the project portable for all team members.</p>
     *
     * @param username the student's username
     * @param subject the email subject
     * @param message the email message body
     */
    public void sendNotification(String username, String subject, String message) {
        String studentEmail = buildStudentEmail(username);

        System.out.println();
        System.out.println("============================================================");
        System.out.println("EMAIL NOTIFICATION");
        System.out.println("To: " + studentEmail);
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + message);
        System.out.println("============================================================");
        System.out.println();
    }

    /**
     * Builds a simple project email address from the username.
     *
     * <p>This method keeps email notification logic simple for the
     * prototype. A future version can replace this with a database
     * lookup from the student table.</p>
     *
     * @param username the student's username
     * @return generated student email address
     */
    private String buildStudentEmail(String username) {
        return username + "@student.example.edu";
    }
}