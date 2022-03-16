package com.nttdata.azure.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nttdata.azure.model.Book;
import com.nttdata.azure.service.BookService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/reactive/api/books")
public class BookController {

	private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/hola")
    public String getMessage() {
    	return "hola";
    }

    @GetMapping(value = "/", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Book> getAllBook(){
        return bookService.getAllBook().delayElements(Duration.ofSeconds(1));
    }

    @GetMapping(value = "/presure/{nro}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Book> getAllBookBackPresure(@PathVariable int nro){
        return bookService.getAllBookBackPresure(nro);
    }

    @GetMapping("/{id}")
    public Mono<Book> findById(@PathVariable Long id){
        return bookService.findById(id);
    }

    @PostMapping("/")
    public Mono<Book> postBook(@RequestBody Book book) {
        return bookService.postBook(book).log();
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Book>>  updateBook(@PathVariable Long id ,@RequestBody Book book) {
        return bookService.updateBook(id,book);

    }
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteBookById(@PathVariable long id){
        return bookService.deleteUser(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
}
