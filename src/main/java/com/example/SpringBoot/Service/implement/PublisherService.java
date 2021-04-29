package com.example.SpringBoot.Service.implement;

import com.example.SpringBoot.Model.Book;
import com.example.SpringBoot.Model.Publisher;
import com.example.SpringBoot.Service.mapping.BeanMapping;

import java.util.List;

public abstract class PublisherService implements BeanMapping<Publisher> {
    public abstract List<Book> getBookById(int id);
}
