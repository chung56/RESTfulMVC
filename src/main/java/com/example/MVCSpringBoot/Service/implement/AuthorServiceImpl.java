package com.example.MVCSpringBoot.Service.implement;

import com.example.MVCSpringBoot.Exceptions.ResourceNotFoundException;
import com.example.MVCSpringBoot.Model.Author;
import com.example.MVCSpringBoot.Model.Book;
import com.example.MVCSpringBoot.Repository.AuthorRepository;
import com.example.MVCSpringBoot.Service.AuthorService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@SuppressWarnings("unchecked")
@NoArgsConstructor
public class AuthorServiceImpl extends AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Page<Author> getAll(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    @Override
    public Author add(Author o) {
        return authorRepository.save(o);
    }

    @Override
    public Author update(Author o, int id) {
        Author author = checkIfIdIsPresentandReturnAuthor(id);
        author.setName(author.getName());
        author.setAddress(author.getAddress());
        return authorRepository.save(author);
    }

    @Override
    public Author getById(int id) {
        return checkIfIdIsPresentandReturnAuthor(id);
    }

    @Override
    public Author deleteById(int id) {
        Author author = checkIfIdIsPresentandReturnAuthor(id);
        authorRepository.deleteById(id);
        return author;
    }


    private Author checkIfIdIsPresentandReturnAuthor( int id )
    {
        if ( !authorRepository.findById( id ).isPresent() )
            throw new ResourceNotFoundException( " Author id = " + id + " not found" );
        else
            return authorRepository.findById( id ).get();
    }


    @Override
    public List<Book> getBookById(int id) {
        return checkIfIdIsPresentandReturnAuthor(id).getBookList();
    }
}
