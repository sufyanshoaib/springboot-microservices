package com.sufyanshoaib.department_service.controller;

import com.sufyanshoaib.department_service.client.EmployeeClient;
import com.sufyanshoaib.department_service.model.Department;
import com.sufyanshoaib.department_service.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentRepository repository;

    @Autowired
    private EmployeeClient employeeClient;

    @PostMapping
    public Department add(@RequestBody Department department) {
        LOGGER.info("Department add: {}", department);
        return repository.addDepartment(department);
    }

    @GetMapping("/{id}")
    public Department findById(@PathVariable  Long id) {
        LOGGER.info("Department findbbyid: {}", id);
        return repository.findById(id);
    }

    @GetMapping("")
    public List<Department> findAll() {
        LOGGER.info("Department findAll");
        return repository.findAll();
    }

    @GetMapping("/with-employees")
    public List<Department> findAllWithEmployees() {
        LOGGER.info("Department findAllWithEmployees");
        List<Department> departments = repository.findAll();
        LOGGER.info("Department all :{}", departments);
        departments.forEach(d ->
        {         LOGGER.info("Department finding employees for : {}", d.getId());

            d.setEmployees(employeeClient.findByDepartment(d.getId()));
        });

        return departments;
    }
}

