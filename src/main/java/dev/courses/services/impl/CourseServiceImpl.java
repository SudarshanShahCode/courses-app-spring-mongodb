package dev.courses.services.impl;

import dev.courses.entities.Course;
import dev.courses.repositories.CourseRepository;
import dev.courses.services.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
        return this.courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("No course found with CourseID : " + courseId));
    }

    @Override
    public Course updateCourse(String courseId, Course course) {
        Course course1 = this.courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("No course found with CourseID : " + courseId));

        Course updatedCourse = new Course(courseId,
                                        course.courseName(),
                                        course.instructor(),
                                        course.courseFee(),
                                        course.duration(),
                                        course.isCourseLive());

        return this.courseRepository.save(updatedCourse);
    }

    @Override
    public Map<String, String> deleteCourse(String courseId) {
        Course course = this.courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("No course found with CourseID : " + courseId));
        this.courseRepository.delete(course);
        return Map.of("Message", "Course with CourseId : " + courseId + " deleted successfully.");
    }
}
