package dev.courses.controllers;

import dev.courses.entities.Course;
import dev.courses.services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(
            summary = "Add a Course",
            description = "We add the course to DB by providing Course object in Request Body. " +
                           "We get the new created Course as response",
            tags = {"courses", "post"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    content = {@Content(schema = @Schema(implementation = Course.class),
                    mediaType = "application/json")})
    })
    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        return new ResponseEntity<>(this.courseService.addCourse(course), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get All Courses",
            description = "We will get all courses in the database",
            tags = {"courses", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
            content = {@Content(schema = @Schema(implementation = List.class),
            mediaType = "application/json")})
    })
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(this.courseService.getAllCourses());
    }

    @Operation(
            summary = "Get One Course",
            description = "We will get course with given ID",
            tags = {"courses", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = Course.class),
                            mediaType = "application/json")})
    })
    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourse(@PathVariable String courseId) {
        return ResponseEntity.ok(this.courseService.getCourse(courseId));
    }

    @Operation(
            summary = "Update Course by ID",
            description = "We will update course with given ID",
            tags = {"courses", "put"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = Course.class),
                            mediaType = "application/json")})
    })
    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(@PathVariable String courseId, @RequestBody Course course) {
        return ResponseEntity.ok(this.courseService.updateCourse(courseId, course));
    }

    @Operation(
            summary = "Delete Course by ID",
            description = "We will delete course with given ID",
            tags = {"courses", "delete"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = Map.class),
                            mediaType = "application/json")})
    })
    @DeleteMapping("/{courseId}")
    public ResponseEntity<Map<String, String>> deleteCourse(@PathVariable String courseId) {
        return ResponseEntity.ok(this.courseService.deleteCourse(courseId));
    }

}
