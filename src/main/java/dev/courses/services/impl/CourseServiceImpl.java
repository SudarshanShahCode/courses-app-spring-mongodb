package dev.courses.services.impl;

import dev.courses.entities.Course;
import dev.courses.repositories.CourseRepository;
import dev.courses.services.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course addCourse(Course course) {
        return this.courseRepository.save(course);
    }

    @Override
    public List<Course> getAllCourses() {
        return this.courseRepository.findAll();
    }

    @Override
    public Course getCourse(String courseId) {
        return null;
    }

    @Override
    public Course updateCourse(String courseId, Course course) {
        return null;
    }

    @Override
    public String deleteCourse(String courseId) {
        return "";
    }
}
