package com.cognizant.student.controller;

import com.cognizant.student.Model.StudentEntity;
import com.cognizant.student.Model.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {

    @Autowired
    StudentRepository repository;

    boolean validateRequest(StudentRequest request){

        if( null == request.getStudentId() || request.getStudentId().isEmpty() ||
                null == request.getFirstName() || request.getFirstName().isEmpty())
            return false;

        return true;
    }

    @GetMapping("/student")
    public Iterable<StudentEntity> getAll(){
        return repository.findAll();
    }

    @GetMapping("/student/{id}")
    public StudentEntity getAll(@PathVariable String id){
        return repository.findById(id).get();
    }



    @PostMapping("/student")
    public String addCourse(@RequestBody StudentRequest request){
        if (!validateRequest(request)) {
            return MessageConstant.ERROR;
        }

        repository.save(new StudentEntity(request.getStudentId(),request.getFirstName(),request.getLastName()));
        return MessageConstant.SUCCESS;
    }

    @PutMapping("/student/{id}")
    public String update(@RequestBody StudentRequest request){

       if (!validateRequest(request)) {
           return MessageConstant.ERROR;
       }

        StudentEntity student = repository.findById(request.getStudentId() ).orElse(null);

        if(null == student)
            return MessageConstant.ERROR;

        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        repository.save(student);


        return MessageConstant.SUCCESS;

    }

    @DeleteMapping("/student/{id}")
    public String update(@PathVariable String id){

        repository.deleteById(id);

        return MessageConstant.SUCCESS;

    }


}
