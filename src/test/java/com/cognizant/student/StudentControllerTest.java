package com.cognizant.student;
        import com.cognizant.student.Model.StudentEntity;
        import com.cognizant.student.Model.StudentRepository;
        import com.fasterxml.jackson.core.type.TypeReference;
        import com.fasterxml.jackson.databind.ObjectMapper;
        import org.junit.Before;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.http.MediaType;
        import org.springframework.test.context.junit4.SpringRunner;
        import org.springframework.test.web.servlet.MockMvc;

        import java.util.List;

        import static org.hamcrest.MatcherAssert.assertThat;
        import static org.hamcrest.Matchers.*;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    StudentRepository repository;

    @Before
    public void clear(){
        repository.deleteAll();
    }

    @Test
    public void nullRequestBodyReturnsErrorMessage() throws Exception{

        String content = mvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content, is("Error"));
    }



    @Test
    public void emptyRequestBodyReturnsErrorMessage() throws Exception{

        String content = mvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\" :  \"\", \"firstname\" : \"\", \"lastname\": \"\"}"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content, is("Error"));
    }

    @Test
    public void successMessageReturnedWhenRequestIsValid() throws Exception{

        String content = mvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\" :  \"1\", \"firstname\" : \"Algorithm\", \"lastname\": \"Analysis\"}"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(content, is("Success"));
    }

    @Test
    public void getAllReturnsAllCourses() throws Exception{

        StudentEntity student = new StudentEntity("20","data struc","Algorithm analysis");
        repository.save(student);

        StudentEntity  course2 = new StudentEntity("222","Architecture","software & hardware");
        repository.save(course2);


        String content = mvc.perform(get("/student").accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();

        List<StudentEntity> returnedstudent = mapper.readValue(content, new TypeReference<List<StudentEntity>>(){});

        assertThat(returnedstudent, containsInAnyOrder(student,course2));
    }


    @Test
    public void getByIdReturnsCorrect() throws Exception{

        StudentEntity  student = new StudentEntity("20","data struc","Algorithm analysis");
        repository.save(student);


        String content = mvc.perform(get("/student/" + student.getStudentId())
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        StudentEntity returnedStudent = mapper.readValue(content, new TypeReference<StudentEntity>(){});
        assertThat(returnedStudent, is(student));
    }

    @Test
    public void updateWithValidIDReturnSuccess() throws Exception{

        StudentEntity  originalStudent = new StudentEntity("20","data struc","Algorithm analysis");
        repository.save(originalStudent);

        StudentEntity  updatedStudent = new StudentEntity("20","Math","calc 2");

        String content = mvc.perform(put("/student/" + originalStudent.getStudentId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"id\" :" + "\""+ originalStudent.getStudentId() + "\""
                        + ", \"firstname\" :" + "\"" + updatedStudent.getFirstName() + "\""
                        + ", \"lastname\" :" + "\""+  updatedStudent.getLastName() + "\"" +"}"))
                .andReturn()
                .getResponse()
                .getContentAsString();



        StudentEntity retrievedStudent = repository.findById(originalStudent.getStudentId()).orElse(null);

        assertThat(updatedStudent, is(retrievedStudent));
    }


    @Test
    public void deleteWithValidIDReturnSuccess() throws Exception{

        StudentEntity  originalStudent = new StudentEntity("20","data struc","Algorithm analysis");
        repository.save(originalStudent);

        String content = mvc.perform(delete("/student/" + originalStudent.getStudentId()))
                .andReturn()
                .getResponse()
                .getContentAsString();


        StudentEntity retrievedStudent = repository.findById(originalStudent.getStudentId()).orElse(null);

        assertThat(null, is(retrievedStudent));
    }
}
