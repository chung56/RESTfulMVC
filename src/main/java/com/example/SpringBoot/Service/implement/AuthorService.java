package com.example.SpringBoot.Service.implement;

import com.example.SpringBoot.Model.Author;
import com.example.SpringBoot.Model.Book;
import com.example.SpringBoot.Service.mapping.BeanMapping;

import java.util.List;

public abstract class AuthorService implements BeanMapping<Author> {
    public abstract List<Book> getBookById(int id);
}


