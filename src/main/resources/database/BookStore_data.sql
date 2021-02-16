INSERT INTO author(author_name, address)
VALUES('Karel Capek','Praha'),
      ('Helen Hadrt','Klatovy'),
	  ('Stephen King','New York'),
	  ('Helena Vondrackova','Pardubice'),
	  ('Petr Novak','Telc');
	  
INSERT INTO publisher(publisher_name, address)
VALUES('Knihy Dobrovsky','Brno'),
      ('Kosmas','Praha'),
	  ('Levne knihy','Plzen'),
	  ('BookPort','Brno'),
	  ('Dobre knihy','Liberec'); 
	   
INSERT INTO book(author_id, publisher_id, book_name, price)
VALUES(1, 3,'RUR', 599),
      (2, 1,'Touha', 299),
      (3, 5,'Dallas 63', 509),
	  (4, 2,'Posledni poutnik', 599),
	  (5, 3,'Bratr', 599);