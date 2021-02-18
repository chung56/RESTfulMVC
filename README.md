# Test programu

  - https://documenter.getpostman.com/view/9427677/TWDTLyXr
  - https://documenter.getpostman.com/view/9427677/TWDTLyXs

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

![N|Center](image/ServiceLayer.png)

```sh
public interface BeanMapping<T> {
    Page<T> getAll(Pageable pageable);
    T add(T o);
    T update(T o, int id);
    T getById(int id);
    T deleteById(int id);
}
```
```sh
public abstract class AuthorService implements BeanMapping<Author> {
    public abstract List<Book> getBookById(int id);
}
```
```sh
public abstract class BookService implements BeanMapping<Book> {
}

```
```sh
public abstract class PublisherService implements BeanMapping<Publisher> {
    public abstract List<Book> getBookById(int id);
}
```

Děděním “AuthorService“ pomocí extends nadefinujeme všechny naše metody. Je tady ještě vytvořená metoda “checkIfIdIsPresentandReturnAuthor“, která kontroluje jestli v databázi existuje hledané “id“. Jestli existuje tak se nám vrátí nějaký záznam jinak vyskčí exception. v příkladu je anotace “@Autowired“, která nám injektuje AuthorRepository objekt do service třídy. 

```sh
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
```


