package com.example.SpringBoot.Controller;

import com.example.SpringBoot.HttpStatus.ResponseWrapper;
import com.example.SpringBoot.Model.Author;
import com.example.SpringBoot.Model.Book;
import com.example.SpringBoot.Service.implement.AuthorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import javax.validation.Valid;

import java.util.List;

import static com.example.SpringBoot.Constants.ApiConstants.MESSAGE_FOR_REGEX_NUMBER_MISMATCH;
import static com.example.SpringBoot.Constants.ApiConstants.REGEX_FOR_NUMBERS;


@Validated
@RestController
@RequestMapping("api/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @ApiOperation(
            value = "Get author",
            produces = MediaType.APPLICATION_JSON_VALUE,
            httpMethod = "GET",
            response = Author.class
    )
    @GetMapping(value = "/{id}")
    public ResponseWrapper<Author> getAuthorById(
            @Valid @Pattern(regexp = REGEX_FOR_NUMBERS, message = MESSAGE_FOR_REGEX_NUMBER_MISMATCH)
            @ApiParam(value = "id")
            @PathVariable(value = "id") String id) {
        return new ResponseWrapper<>(authorService.getById(Integer.parseInt(id)), HttpStatus.OK);
    }

    @ApiOperation(
            value = "Get all author",
            produces = MediaType.APPLICATION_JSON_VALUE,
            httpMethod = "GET",
            response = Author.class
    )
    @GetMapping()
    public ResponseWrapper<Page<Author>> getAuthorAll(Pageable pageable) {
        return new ResponseWrapper<>(authorService.getAll(pageable), HttpStatus.OK);
    }

    @ApiOperation(
            value = "Add author",
            produces = MediaType.APPLICATION_JSON_VALUE,
            httpMethod = "POST",
            response = Author.class
    )
    @PostMapping
    public ResponseWrapper<Author> createAuthor(@ApiParam(value = "author")
                                                @Valid @RequestBody Author author) {
        return new ResponseWrapper<>(authorService.add(author), HttpStatus.OK);
    }

    @ApiOperation(
            value = "Delete author",
            produces = MediaType.APPLICATION_JSON_VALUE,
            httpMethod = "DELETE",
            response = Author.class
    )
    @DeleteMapping(value = "/{id}")
    public ResponseWrapper<Author> deleteAuthor(
            @Valid @Pattern(regexp = REGEX_FOR_NUMBERS, message = MESSAGE_FOR_REGEX_NUMBER_MISMATCH)
            @ApiParam(value = "id")
            @PathVariable(value = "id") String id) {
        return new ResponseWrapper<>(authorService.deleteById(Integer.parseInt(id)), HttpStatus.OK);
    }

    @ApiOperation(
            value = "Update author",
            produces = MediaType.APPLICATION_JSON_VALUE,
            httpMethod = "PATCH",
            response = Author.class
    )
    @PatchMapping(value = "/{id}")
    public ResponseWrapper<Author> UpdateAuthor(
            @Valid @Pattern(regexp = REGEX_FOR_NUMBERS, message = MESSAGE_FOR_REGEX_NUMBER_MISMATCH)
            @RequestBody Author author,
            @PathVariable(value = "id") String id) {
        return new ResponseWrapper<>(authorService.update(author, Integer.parseInt(id)), HttpStatus.OK);
    }

    @ApiOperation(
            value = "Get author by book",
            produces = MediaType.APPLICATION_JSON_VALUE,
            httpMethod = "GET",
            response = Author.class
    )
    @GetMapping(value = "/{id}/books")
    public ResponseWrapper<List<Book>> getAuthorBooksById(
            @Valid @Pattern(regexp = REGEX_FOR_NUMBERS, message = MESSAGE_FOR_REGEX_NUMBER_MISMATCH)
            @PathVariable(value = "id") String id) {
        return new ResponseWrapper<>(authorService.getBookById(Integer.parseInt(id)), HttpStatus.OK);
    }
}
