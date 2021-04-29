package com.example.SpringBoot.Controller;

import com.example.SpringBoot.Exceptions.ResourceNotFoundException;
import com.example.SpringBoot.HttpStatus.ResponseWrapper;
import com.example.SpringBoot.Model.Book;
import com.example.SpringBoot.Model.Publisher;
import com.example.SpringBoot.Service.implement.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import java.util.List;

import static com.example.SpringBoot.Constants.ApiConstants.MESSAGE_FOR_REGEX_NUMBER_MISMATCH;
import static com.example.SpringBoot.Constants.ApiConstants.REGEX_FOR_NUMBERS;

@Validated
@RestController
@RequestMapping("api/publisher")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @GetMapping(value = "/{id}")
    public ResponseWrapper<Publisher> getPublisherById(
            @Valid @Pattern(regexp = REGEX_FOR_NUMBERS, message = MESSAGE_FOR_REGEX_NUMBER_MISMATCH) @PathVariable(value = "id") String id)
            throws ResourceNotFoundException {
        return new ResponseWrapper<>(publisherService.getById(Integer.parseInt(id)), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseWrapper<Page<Publisher>> getPublisherAll(Pageable pageable) {
        return new ResponseWrapper<>(publisherService.getAll(pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseWrapper<Publisher> createPublisher(@Valid @RequestBody Publisher publisher)
            throws ResourceNotFoundException {
        return new ResponseWrapper<>(publisherService.add(publisher), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseWrapper<Publisher> deletePublisher(
            @Valid @Pattern(regexp = REGEX_FOR_NUMBERS, message = MESSAGE_FOR_REGEX_NUMBER_MISMATCH) @PathVariable(value = "id") String id)
            throws ResourceNotFoundException {
        return new ResponseWrapper<>(publisherService.deleteById(Integer.parseInt(id)), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}")
    public ResponseWrapper<Publisher> UpdateAuthor(@Valid @RequestBody Publisher publisher,
                                                   @Valid @Pattern(regexp = REGEX_FOR_NUMBERS, message = MESSAGE_FOR_REGEX_NUMBER_MISMATCH) @PathVariable(value = "id") String id)
            throws ResourceNotFoundException {
        return new ResponseWrapper<>(publisherService.update(publisher, Integer.parseInt(id)), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/books")
    public ResponseWrapper<List<Book>> getPublisherBooksById(
            @Valid @Pattern(regexp = REGEX_FOR_NUMBERS, message = MESSAGE_FOR_REGEX_NUMBER_MISMATCH) @PathVariable(value = "id") String id)
            throws ResourceNotFoundException {
        return new ResponseWrapper<>(publisherService.getBookById(Integer.parseInt(id)), HttpStatus.OK);
    }
}
