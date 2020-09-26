package com.saurabh.springsecurity.controller;

import com.saurabh.springsecurity.model.Employee;
import com.saurabh.springsecurity.model.Student;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v2/")
public class EmployeeController {

    private final static List<Employee> EMPLOYEE_LIST = new ArrayList<Employee>() {{
        add(new Employee(10, "Saurabh Kumar", "uuuuu"));
        add(new Employee(20, "Saus Kumar", "hhhh"));
        add(new Employee(30, "Sakshi Kumar", "ggg"));
        add(new Employee(40, "Shashi Kumar", "gggkjl"));
    }};

    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public List<Employee> getStudents() {
        return EMPLOYEE_LIST;
    }

    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public String addEmployee(@RequestBody Employee e) {
        EMPLOYEE_LIST.add(e);
        return "Successfully added";
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
    public String doDeleteEmployee(@RequestBody Employee e) {
        EMPLOYEE_LIST.remove(e);
        return "Successfully deleted";
    }
}


