package com.example.SpringWeb.Dao;

import com.example.SpringWeb.Model.Student;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Braysen TC
 */

@Repository
public interface StudentDao extends ReactiveMongoRepository<Student,String>{
    
}
