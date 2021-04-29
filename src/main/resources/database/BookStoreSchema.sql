DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS publisher;
DROP TABLE IF EXISTS book;

CREATE TABLE author(
   author_id INT GENERATED ALWAYS AS IDENTITY,
   author_name VARCHAR(255) NOT NULL,
	address VARCHAR(255) NOT NULL,
   PRIMARY KEY(author_id)
);

CREATE TABLE publisher(
   publisher_id INT GENERATED ALWAYS AS IDENTITY,
   publisher_name VARCHAR(255) NOT NULL,
	address VARCHAR(255) NOT NULL,
   PRIMARY KEY(publisher_id)
);

CREATE TABLE book(
   book_id INT GENERATED ALWAYS AS IDENTITY,
   author_id INT REFERENCES author(author_id),
   publisher_id INT REFERENCES publisher(publisher_id),
   book_name VARCHAR(255) NOT NULL,
   price double precision NOT NULL,
   PRIMARY KEY(book_id)
);

ALTER TABLE ONLY public.book
    ADD CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES public.author(author_id);
ALTER TABLE ONLY public.book
    ADD CONSTRAINT fk_publisher FOREIGN KEY (publisher_id) REFERENCES public.publisher(publisher_id);


