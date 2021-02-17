package com.example.MVCSpringBoot.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "author")
public class Author{

    @Id
    @Column(name = "author_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "author_name")
    @NotNull
    private String name;

    @Column(name = "address")
    @NotNull
    private String address;

    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Book> bookList = new ArrayList<>();

    public void addBook(Book book )
    {
        this.bookList.add( book );
    }

}
