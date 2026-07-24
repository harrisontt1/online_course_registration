package com.example.registration.services;

import com.example.registration.model.Course;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer responsible for managing course data.
 *
 * <p>This class stores the available courses, supports administrator
 * add/update/delete actions, tracks enrollment counts, and provides
 * course lookup methods used by the registration process.</p>
 */
@Service
public class CourseService {

    private final List<Course> courseList = new ArrayList<>();

    /**
     * Creates the sample courses used by the application.
     */
    public CourseService() {
        courseList.add(new Course(
                "CMSC495",
                "Capstone",
                "Dr. Smith",
                3,
                "MWF 10AM",
                2,
                0,
                "CMSC345"
        ));

        courseList.add(new Course(
                "CMSC451",
                "Algorithms",
                "Dr. Lee",
                3,
                "TTh 2PM",
                2,
                0,
                ""
        ));
    }

    /**
     * Retrieves all available courses.
     *
     * @return list of all courses
     */
    public List<Course> getAllCourse() {
        return courseList;
    }

    /**
     * Adds a new course if the course ID does not already exist.
     *
     * @param course course to add
     * @return true if course is added, false if duplicate ID exists
     */
    public boolean addCourse(Course course) {
        if (findCourseById(course.getId()) != null) {
            return false;
        }

        if (course.getMaxCapacity() <= 0) {
            course.setMaxCapacity(2);
        }

        course.setEnrolledCount(0);
        courseList.add(course);

        return true;
    }

    /**
     * Updates an existing course.
     *
     * @param courseId course ID to update
     * @param updatedCourse updated course information
     * @return true if updated, false if course was not found
     */
    public boolean updateCourse(String courseId, Course updatedCourse) {
        Course existingCourse = findCourseById(courseId);

        if (existingCourse == null) {
            return false;
        }

        existingCourse.setName(updatedCourse.getName());
        existingCourse.setInstructor(updatedCourse.getInstructor());
        existingCourse.setCredits(updatedCourse.getCredits());
        existingCourse.setMeetingTime(updatedCourse.getMeetingTime());
        existingCourse.setMaxCapacity(updatedCourse.getMaxCapacity());
        existingCourse.setPrerequisiteCourseId(updatedCourse.getPrerequisiteCourseId());

        return true;
    }

    /**
     * Deletes a course if it exists.
     *
     * @param courseId course ID to delete
     * @return true if deleted, false if course was not found
     */
    public boolean deleteCourse(String courseId) {
        Course existingCourse = findCourseById(courseId);

        if (existingCourse == null) {
            return false;
        }

        courseList.remove(existingCourse);

        return true;
    }

    /**
     * Checks if a course has at least one available seat.
     *
     * @param courseId course ID to check
     * @return true if seat is available, false otherwise
     */
    public boolean hasAvailableSeat(String courseId) {
        Course course = findCourseById(courseId);

        if (course == null) {
            return false;
        }

        return course.getEnrolledCount() < course.getMaxCapacity();
    }

    /**
     * Increases the enrolled count after successful registration.
     *
     * @param courseId course ID
     */
    public void increaseEnrollment(String courseId) {
        Course course = findCourseById(courseId);

        if (course != null && course.getEnrolledCount() < course.getMaxCapacity()) {
            course.setEnrolledCount(course.getEnrolledCount() + 1);
        }
    }

    /**
     * Decreases the enrolled count after successful withdrawal.
     *
     * @param courseId course ID
     */
    public void decreaseEnrollment(String courseId) {
        Course course = findCourseById(courseId);

        if (course != null && course.getEnrolledCount() > 0) {
            course.setEnrolledCount(course.getEnrolledCount() - 1);
        }
    }

    /**
     * Finds a course by course ID.
     *
     * @param courseId course ID to search for
     * @return Course if found, otherwise null
     */
    public Course findCourseById(String courseId) {
        for (Course course : courseList) {
            if (course.getId().equalsIgnoreCase(courseId)) {
                return course;
            }
        }

        return null;
    }
}
