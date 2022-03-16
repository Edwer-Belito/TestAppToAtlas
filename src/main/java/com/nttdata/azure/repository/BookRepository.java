package com.nttdata.azure.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.azure.model.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, Long >{

}
