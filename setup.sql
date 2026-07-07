-- 1. Create the Database
CREATE DATABASE IF NOT EXISTS online_registration;
USE online_registration;

-- 2. Create the Tables
CREATE TABLE student (
                         student_id INT PRIMARY KEY AUTO_INCREMENT,
                         first_name VARCHAR(100),
                         last_name VARCHAR(100),
                         email VARCHAR(150) NOT NULL UNIQUE
);

CREATE TABLE course (
                        course_id INT PRIMARY KEY AUTO_INCREMENT,
                        title VARCHAR(100) NOT NULL,
                        description TEXT,
                        credits INT NOT NULL
);

CREATE TABLE section (
                         section_id INT PRIMARY KEY AUTO_INCREMENT,
                         course_id INT NOT NULL,
                         schedule VARCHAR(100),
                         capacity INT NOT NULL,
                         enrolled_count INT DEFAULT 0,
                         FOREIGN KEY (course_id) REFERENCES course(course_id)
);

CREATE TABLE enrollment (
                            enrollment_id INT PRIMARY KEY AUTO_INCREMENT,
                            student_id INT NOT NULL,
                            section_id INT NOT NULL,
                            status VARCHAR(20) DEFAULT 'ENROLLED',
                            FOREIGN KEY (student_id) REFERENCES student(student_id),
                            FOREIGN KEY (section_id) REFERENCES section(section_id)
);

-- 3. Insert baseline data
INSERT INTO course (title, description, credits)
VALUES ('Introduction to Computer Science', 'Foundational concepts.', 3);