package com.example.MVCSpringBoot.Controller;

import com.example.MVCSpringBoot.HttpStatus.ResponseWrapper;
import com.example.MVCSpringBoot.Model.Book;
import com.example.MVCSpringBoot.Service.implement.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import static com.example.MVCSpringBoot.Constants.ApiConstants.MESSAGE_FOR_REGEX_NUMBER_MISMATCH;
import static com.example.MVCSpringBoot.Constants.ApiConstants.REGEX_FOR_NUMBERS;

@Validated
@RestController
@RequestMapping("api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/{id}")
    public ResponseWrapper<Book> getPublisherById(
            @Valid @Pattern(regexp = REGEX_FOR_NUMBERS, message = MESSAGE_FOR_REGEX_NUMBER_MISMATCH) @PathVariable(value = "id") String id )
    {
        return new ResponseWrapper<>( bookService.getById( Integer.parseInt(id) ), HttpStatus.OK );
    }

    @GetMapping()
    public ResponseWrapper<Page<Book>> getPublisherAll(Pageable pageable )
    {
        return new ResponseWrapper<>( bookService.getAll( pageable ), HttpStatus.OK );
    }

    @PostMapping
    public ResponseWrapper<Book> createPublisher( @Valid @RequestBody Book book )
    {
        return new ResponseWrapper<>( bookService.add( book ), HttpStatus.OK );
    }

    @DeleteMapping(value = "/{id}")
    public ResponseWrapper<Book> deletePublisher(
            @Valid @Pattern(regexp = REGEX_FOR_NUMBERS, message = MESSAGE_FOR_REGEX_NUMBER_MISMATCH) @PathVariable(value = "id") String id )
    {
        return new ResponseWrapper<>( bookService.deleteById( Integer.parseInt( id ) ), HttpStatus.OK );
    }

    @PatchMapping(value = "/{id}")
    public ResponseWrapper<Book> UpdateAuthor( @Valid @RequestBody Book book,
                                               @Valid @Pattern(regexp = REGEX_FOR_NUMBERS, message = MESSAGE_FOR_REGEX_NUMBER_MISMATCH) @PathVariable(value = "id") String id )
    {
        return new ResponseWrapper<>( bookService.update( book, Integer.parseInt( id ) ), HttpStatus.OK );
    }

}
