-- Base de données de Morix
--
-- Remplissage de la base de données.
--
-- @author Matthias Brun
--

-- Utilisation de la base morix.
USE morix;

-- Insertion des produits dans la base.
INSERT INTO Produit VALUES ("11A", "produit un A    ", 10, 1);
INSERT INTO Produit VALUES ("22A", "produit deux A  ", 20, 2);
INSERT INTO Produit VALUES ("33A", "produit trois A ", 30, 3);
INSERT INTO Produit VALUES ("44A", "produit quatre A", 40, 4);
INSERT INTO Produit VALUES ("55A", "produit cinq A  ", 50, 5);

INSERT INTO Produit VALUES ("11AA", "produit un A super long    ", 10, 10);
INSERT INTO Produit VALUES ("22AA", "produit deux A super long  ", 20, 20);
INSERT INTO Produit VALUES ("33AA", "produit trois A super long ", 30, 30);
INSERT INTO Produit VALUES ("44AA", "produit quatre A super long", 40, 40);
INSERT INTO Produit VALUES ("55AA", "produit cinq A super long  ", 50, 50);

INSERT INTO Produit VALUES ("11Z", "produit un Z    ", 10.50, 1);
INSERT INTO Produit VALUES ("22Z", "produit deux Z  ", 20.50, 2);
INSERT INTO Produit VALUES ("33Z", "produit trois Z ", 30.50, 3);
INSERT INTO Produit VALUES ("44Z", "produit quatre Z", 40.50, 4);
INSERT INTO Produit VALUES ("55Z", "produit cinq Z  ", 50.50, 5);

INSERT INTO Produit VALUES ("111Z", "produit un 10Z    ", 10000.50, 110);
INSERT INTO Produit VALUES ("222Z", "produit deux 10Z  ", 20000.50, 220);
INSERT INTO Produit VALUES ("333Z", "produit trois 10Z ", 30000.50, 330);
INSERT INTO Produit VALUES ("444Z", "produit quatre 10Z", 40000.50, 440);
INSERT INTO Produit VALUES ("555Z", "produit cinq 10Z  ", 50000.50, 550);

INSERT INTO Produit VALUES ("111ZZ", "produit un 10ZZ super long    ", 10000.50, 10000);
INSERT INTO Produit VALUES ("222ZZ", "produit deux 10ZZ super long  ", 20000.50, 20000);
INSERT INTO Produit VALUES ("333ZZ", "produit trois 10ZZ super long ", 30000.50, 30000);
INSERT INTO Produit VALUES ("444ZZ", "produit quatre 10ZZ super long", 40000.50, 40000);
INSERT INTO Produit VALUES ("555ZZ", "produit cinq 10ZZ super long    ", 50000.50, 50000);

