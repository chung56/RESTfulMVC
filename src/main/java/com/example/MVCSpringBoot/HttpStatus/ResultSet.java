package com.example.MVCSpringBoot.HttpStatus;

import com.example.MVCSpringBoot.Exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ResultSet<T> {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private T data;

    private ResultSet()
    {
        this.timestamp = LocalDateTime.now();
    }

    public ResultSet( T o, HttpStatus status ) throws ResourceNotFoundException
    {
        this();
        if ( o == null || ( o instanceof List && ( ( List ) o ).isEmpty() ) )
            throw new ResourceNotFoundException( "No Content Found" );
        this.status = status;
        this.data = o;
    }
}
