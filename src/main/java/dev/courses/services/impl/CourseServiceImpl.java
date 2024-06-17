package dev.courses.services.impl;

import dev.courses.entities.Course;
import dev.courses.entities.ResponseDto;
import dev.courses.exceptions.CourseNotFoundException;
import dev.courses.repositories.CourseRepository;
import dev.courses.services.CourseService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final MongoTemplate mongoTemplate;

    public CourseServiceImpl(CourseRepository courseRepository, MongoTemplate mongoTemplate) {
        this.courseRepository = courseRepository;
        this.mongoTemplate = mongoTemplate;
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
                .orElseThrow(() -> new CourseNotFoundException("No course found with CourseID : " + courseId));
    }

    @Override
    public Course updateCourse(String courseId, Course course) {
        Course course1 = this.courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("No course found with CourseID : " + courseId));

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
                .orElseThrow(() -> new CourseNotFoundException("No course found with CourseID : " + courseId));
        this.courseRepository.delete(course);
        return Map.of("Message", "Course with CourseId : " + courseId + " deleted successfully.");
    }

    @Override
    public List<Course> findByInstructorAndCourseFee(String instructor, Double courseFee) {
        // 1st stage of Aggregation pipeline
        MatchOperation matchOperation1 = match(new Criteria("instructor").is(instructor));

        // 2nd stage of Aggregation pipeline
        MatchOperation matchOperation2 = match(new Criteria("courseFee").gt(courseFee));

        // created Aggregation pipeline
        Aggregation aggregation = newAggregation(matchOperation1, matchOperation2);

        // get the result from Aggregation pipeline
        AggregationResults<Course> aggregationResults = mongoTemplate.aggregate(aggregation,
                                                                "courses", Course.class);

        return aggregationResults.getMappedResults();
    }

    // Filter all records by instructor
    // count of courses
    // average price

    // Result -> instructor, count, avgprice
    @Override
    public ResponseDto findByInstructorMatchAndGroup(String instructor) {
        // 1st stage of Aggregation pipeline
        MatchOperation matchOperation1 = match(new Criteria("instructor").is(instructor));

        // 2nd stage -> group operation
        GroupOperation groupOperation = group("instructor")
                                            .count().as("totalCourses")
                                            .avg("courseFee").as("averageCoursePrice");

        Aggregation aggregation = newAggregation(matchOperation1, groupOperation);

        AggregationResults<ResponseDto> aggregationResults = mongoTemplate.aggregate(aggregation,
                                                            "courses", ResponseDto.class);

        return aggregationResults.getUniqueMappedResult();
    }

}
