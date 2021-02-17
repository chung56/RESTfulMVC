package com.example.MVCSpringBoot.Service.implement;

import com.example.MVCSpringBoot.Model.Book;
import com.example.MVCSpringBoot.Model.Publisher;
import com.example.MVCSpringBoot.Service.mapping.BeanMapping;

import java.util.List;

public abstract class PublisherService implements BeanMapping<Publisher> {
    public abstract List<Book> getBookById(int id);
}
