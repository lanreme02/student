package com.cognizant.student.controller;

import com.cognizant.student.Model.StudentEntity;
import com.cognizant.student.Model.StudentRepository;
import com.cognizant.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {

    @Autowired
    StudentRepository repository;

    @Autowired
    StudentService studentService;


    @GetMapping("/student")
    public Iterable<StudentEntity> getAll(){
        return studentService.getAllStudent();
    }

    @GetMapping("/student/{id}")
    public StudentEntity getAll(@PathVariable String id){
        return studentService.getStudent(id);
    }



    @PostMapping("/student")
    public String addCourse(@RequestBody StudentRequest request){
        if (!studentService.validateRequestBody(request)) {
            return MessageConstant.ERROR;
        }
        studentService.createStudent(request);

        return MessageConstant.SUCCESS;
    }

    @PutMapping("/student/{id}")
    public String update(@RequestBody StudentRequest request){

       if (!studentService.validateRequestBody(request)) {
           return MessageConstant.ERROR;
       }
       return studentService.updateStudent(request)? MessageConstant.SUCCESS : MessageConstant.ERROR;

    }

    @DeleteMapping("/student/{id}")
    public String delete(@PathVariable String id){

            studentService.deleteStudent(id);

        return MessageConstant.SUCCESS;

    }


}
