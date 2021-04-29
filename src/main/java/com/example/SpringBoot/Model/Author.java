package com.example.SpringBoot.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "author")
@ApiModel(value = "Author", description = "Information about author address.")
public class Author {

    @Id
    @Column(name = "author_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Author ID", example = "1")
    private int id;

    @Column(name = "author_name")
    @NotNull
    @ApiModelProperty(value = "Author name", example = "J.K. Rowling")
    private String name;

    @Column(name = "address")
    @NotNull
    @ApiModelProperty(value = "Author address", example = "UK, London")
    private String address;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Book> bookList = new ArrayList<>();

    public void addBook(Book book) {
        this.bookList.add(book);
    }

}
