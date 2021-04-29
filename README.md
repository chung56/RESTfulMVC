# Test programu

  - https://documenter.getpostman.com/view/9427677/TWDTLyXr
  - https://documenter.getpostman.com/view/9427677/TWDTLyXs

pozn: nefunguje book asi chyba v kodu a nebo spatne navrazena databaze

## Databázový model

Představme si jednoduchý scénář, kde máme autora, knížku a vydavatele. Autor a vydavatel mají relaci one-to-many s knížkami.

![N|Solid](image/database.png)



## JPA Entity třídy

Nadefinujeme si entity knihy, kde máme 5 atributů: id, name, author, publisher, price.

```sh
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book implements Serializable {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(name = "book_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id", nullable = false)
    Author author;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "publisher_id", nullable = false)
    Publisher publisher;

    @NotNull
    @Column(name = "price")
    private double price;
}
```

Nejsou zde žádné Gettery a Settery. Vše je nastaveno pomocí pluginu “Lombok” pomocí anotace “@Data”. Konstruktory jsou nastaveny pomocí “@AllArgsConstructor ” a “@NoArgsConstructor” anotace. “@NoArgsConstructor” je potřeba pro JPA. Pozn. pomocí Lomboku dosáhneme čistého kódu.

Použitím ”@Entity” nadefinujeme třídu jako Entity class, která bude brát data z tabulky “book“ pomocí “@Table“ anotace. Id je tady nastaven jako primární klíč pomocí “@Id“ a automaticky si generuje id pomocí “@GeneratedValue“.

V Entitě “book“ může mít každý “publisher“ a “author“ více knížek, proto “book“ má relaci Many-to-One s těmito entitami. Je nastavená pomocí “@ManyToOne“ a foreign key je nadefinován v “@JoinColumn“. Zde jsem použil “Cascade.PERSIST“ pro Many-to-One relaci. To znamená, že když přidám knížku s novým autorem a publisherem, následně vymažu knížku tak autor a vydavatel mi zůstane uložený v databázi.

```sh
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "publisher")
public class Publisher implements Serializable {

    @Id
    @Column(name = "publisher_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(name = "publisher_name")
    private String name;

    @NotNull
    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Book> bookList = new ArrayList<>();
}
```

Tato entita je podobná “book“ ale má jednu metodu navíc a jiné atributy. Zde se ukládá List knih, které jsou vydávány stejným publisherem. 1 publisher může mít několik knih “@OneToMany“  relace s knihami. Aby byl publisher bidiretional použijeme “mappedBy = "publisher" “ a  “usedCascadeType.ALL“. Když bude publisher smazán, tak se smaže kniha, která patřila publisherovi.

Pro přidání nové knihy je potřeba manuálně aktualizovat bookList s relevantním autorem. Proto je tam funkce “addBook“. Stejně to uděláme pro entitu “author“.



## Vytvoření Repositories a přístup k datům z databáze

Zde používám implementaci JpaRepository interface pro vytvořené modely. Proto se musí vytvořit Interface pro všechny vytvořené modely.

```sh
@Repository
public interface AuthorRepository extends JpaRepository<Author,Integer> {
}
```
```sh
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
```
```sh
@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
}
```

JPA Interface obsahuje meotdy save(), findById(), deleteById() atd.


## Vytvoření Servisní třídy

Zde se nadefinují všechny funkce, které bude používat RestController. Všechny Controllery mají CRUD funkci, která je nadefinovaná v BeanMapping, kde se vytváří servisní třídy.

![N|Solid](image/ServiceLayer.png)


