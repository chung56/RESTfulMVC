package com.example.MVCSpringBoot.Service.implement;

import com.example.MVCSpringBoot.Model.Author;
import com.example.MVCSpringBoot.Model.Book;
import com.example.MVCSpringBoot.Service.mapping.BeanMapping;

import java.util.List;

public abstract class AuthorService implements BeanMapping<Author> {
    public abstract List<Book> getBookById(int id);
}
