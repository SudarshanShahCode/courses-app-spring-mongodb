package dev.courses.services;

import dev.courses.entities.Course;

import java.util.List;

public interface CourseService {

    // add the course
    Course addCourse(Course course);

    // get all courses
    List<Course> getAllCourses();

    // get course by Id
    Course getCourse(String courseId);

    // update course
    Course updateCourse(String courseId, Course course);

    // delete course
    String deleteCourse(String courseId);
}
