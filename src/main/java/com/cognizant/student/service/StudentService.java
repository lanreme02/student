package com.cognizant.student.service;

import com.cognizant.student.Model.StudentEntity;
import com.cognizant.student.Model.StudentRepository;
import com.cognizant.student.controller.StudentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService{

    @Autowired
    StudentRepository repository;

  public  boolean validateRequestBody(StudentRequest request){

        if( null == request.getStudentId() || request.getStudentId().isEmpty() ||
                null == request.getFirstName() || request.getFirstName().isEmpty()
        ||  null == request.getLastName() || request.getLastName().isEmpty()) {

            return false;
        }

        return true;
    }

    public Iterable<StudentEntity> getAllStudent(){
        return  repository.findAll();
    }

    public StudentEntity getStudent(String id){

        return repository.findById(id).orElse(null );
    }
    public void createStudent(StudentRequest request){

        repository.save(new StudentEntity(request.getStudentId(),request.getFirstName(),request.getLastName()));

    }
    public boolean updateStudent(StudentRequest request){

        StudentEntity student = repository.findById(request.getStudentId() ).orElse(null);

        if(null == student)
            return  false;

        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        repository.save(student);

        return true;
    }

    public void deleteStudent(String id){

       repository.deleteById(id);
    }
}
