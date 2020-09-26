package com.saurabh.springsecurity.controller;

import com.saurabh.springsecurity.model.Student;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class StudentController {

    private final static List<Student> STUDENT_LIST = new ArrayList<Student>() {{
        add(new Student(1, "Saurabh Kumar", "Saharsa"));
        add(new Student(2, "Saus Kumar", "Sahara"));
        add(new Student(3, "Sakshi Kumar", "Remove"));
        add(new Student(4, "Shashi Kumar", "XXX"));
    }};

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public List<Student> getStudents() {
        return STUDENT_LIST;
    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.GET)
    public Student getStudentById(@PathVariable int id) {
        return STUDENT_LIST.stream().filter(student -> (student.getId() == id)).findFirst()
                .orElseThrow(() -> new RuntimeException("Id:" + id + " does not exists"));
    }
}
