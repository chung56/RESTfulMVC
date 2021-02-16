package com.example.MVCSpringBoot.Service.implement;

import com.example.MVCSpringBoot.Exceptions.ResourceNotFoundException;
import com.example.MVCSpringBoot.Model.Author;
import com.example.MVCSpringBoot.Model.Book;
import com.example.MVCSpringBoot.Model.Publisher;
import com.example.MVCSpringBoot.Repository.AuthorRepository;
import com.example.MVCSpringBoot.Repository.BookRepository;
import com.example.MVCSpringBoot.Repository.PublisherRepository;
import com.example.MVCSpringBoot.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl extends BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Page<Book> getAll( Pageable pageable )
    {
        return bookRepository.findAll( pageable );
    }

    @Override
    public Book add( Book o )
    {
        //if author n publisher gets an id we can search the db and assign them
        if ( o.getAuthor().getId() != 0 )
        {
            Author author = checkIfIdIsPresentandReturnAuthor( o.getAuthor().getId() );
            o.setAuthor( author );
            author.addBook( o );
        }
        if ( o.getPublisher().getId() != 0 )
        {
            Publisher publisher = checkIfIdIsPresentandReturnPublisher( o.getPublisher().getId() );
            o.setPublisher( publisher );
            publisher.addBook( o );
        }

        return bookRepository.save( o );
    }

    @Override
    public Book update( Book o, int id ) throws ResourceNotFoundException
    {

        Book oldBook = checkIfIdIsPresentandReturnBook( id );
        if ( o.getName() != null )
            oldBook.setName( o.getName() );
        if ( o.getAuthor() != null )
        {
            Author author;
            if ( o.getAuthor().getId() != 0 )
            {
                //get the author by Id
                author = checkIfIdIsPresentandReturnAuthor( o.getAuthor().getId() );
                author.addBook( oldBook );
            }
            else
                author = o.getAuthor();

            oldBook.setAuthor( author );

        }
        if ( o.getPublisher() != null )
        {
            Publisher publisher;
            if ( o.getPublisher().getId() != 0 )
            {
                publisher = checkIfIdIsPresentandReturnPublisher( o.getPublisher().getId() );
                publisher.addBook( oldBook );
            }
            else
                publisher = o.getPublisher();
            oldBook.setPublisher( publisher );
        }
        if ( o.getPrice() != 0 )
            oldBook.setPrice( o.getPrice() );
        return bookRepository.save( oldBook );
    }

    @Override
    public Book getById( int id ) throws ResourceNotFoundException
    {
        return checkIfIdIsPresentandReturnBook( id );
    }

    @Override
    public Book deleteById( int id ) throws ResourceNotFoundException
    {
        Book book = checkIfIdIsPresentandReturnBook( id );
        bookRepository.deleteById( id );
        return book;
    }

    private Book checkIfIdIsPresentandReturnBook( int id )
    {
        if ( !bookRepository.findById( id ).isPresent() )
            throw new ResourceNotFoundException( " Book id=" + id + " not found" );
        else
            return bookRepository.findById( id ).get();
    }

    private Author checkIfIdIsPresentandReturnAuthor( int id )
    {
        if ( !authorRepository.findById( id ).isPresent() )
            throw new ResourceNotFoundException( " Author id = " + id + " not found" );
        else
            return authorRepository.findById( id ).get();
    }

    private Publisher checkIfIdIsPresentandReturnPublisher( int id )
    {
        if ( !publisherRepository.findById( id ).isPresent() )
            throw new ResourceNotFoundException( " Publisher id = " + id + " not found" );
        else
            return publisherRepository.findById( id ).get();
    }
}
