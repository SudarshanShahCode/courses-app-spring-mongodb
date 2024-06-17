package dev.courses.repositories;

import dev.courses.entities.Course;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CourseRepository extends MongoRepository<Course, String> {

    // 1. find all courses by instructor, where course Fee is greater than 3000
    @Aggregation(pipeline = {" {$match :  {instructor :  ?0}}", "{$match: {courseFee: {$gt: ?1}}} "})
    List<Course> findByInstructorAndCourseFee(String instructor, Double courseFee);
}
