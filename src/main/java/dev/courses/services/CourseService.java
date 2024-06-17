package dev.courses.services;

import dev.courses.entities.Course;
import dev.courses.entities.ResponseDto;

import java.util.List;
import java.util.Map;

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
    Map<String, String> deleteCourse(String courseId);

    // find all courses by Instructor and Course Fee
    List<Course> findByInstructorAndCourseFee(String instructor, Double courseFee);

    ResponseDto findByInstructorMatchAndGroup(String instructor);
}
