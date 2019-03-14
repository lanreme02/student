package com.cognizant.student.Model;

import org.springframework.data.repository.CrudRepository;
        import org.springframework.stereotype.Component;

@Component
public interface  StudentRepository  extends CrudRepository<StudentEntity ,String> {

}
