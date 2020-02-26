package com.example.SpringWeb.Rest;

import com.example.SpringWeb.Dao.StudentDao;
import com.example.SpringWeb.Model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author Braysen TC
 */
@RestController
@RequestMapping("/student")
public class StudentRestService<ResposeEntity>{
    
    private ResponseEntity notFound=ResponseEntity.notFound().build();
    
    //Importamos la clase StudentDao para acceder a sus propiedades
    @Autowired
    private StudentDao studentDao;
    
    @GetMapping
    public Flux<Student>getStudents(){
        return studentDao.findAll();
    }
    
    @GetMapping("/{id}")
    public Mono<ResposeEntity>getStudentById(@PathVariable("id") String id){
        return (Mono<ResposeEntity>)studentDao.findById(id).
                map(student -> new ResponseEntity<Student>(student,HttpStatus.OK)).
                defaultIfEmpty(notFound);
    }
    
    @PostMapping
    public Mono<Student>createStudent(@RequestBody Student student){
        return studentDao.save(student);
    }
    
    @DeleteMapping("/{id}")
    public Mono<Void>deleteStudentById(@PathVariable("id") String id){
        return studentDao.deleteById(id);
    }
    
    @PutMapping("/{id}")
    public Mono<ResposeEntity>updateStudents(@RequestBody Student student, @PathVariable("id") String id){
        return (Mono<ResposeEntity>)studentDao.findById(id).flatMap(cur -> {
            cur.setAddress(student.getAddress());
            cur.setAge(student.getAge());
            return studentDao.save(cur);
        }).map(student1 -> new ResponseEntity<Student>(student1,HttpStatus.OK)).defaultIfEmpty(notFound);
    }
}
