-- Base de donnée de Morix
--
-- Création de la base de données.
--
-- @author Matthias Brun
--

-- Création de la base.
CREATE DATABASE morix;

-- Création de l'utilisateur de la base.
CREATE USER 'morix'@'localhost' IDENTIFIED BY 'Mom0r|xx';

-- Déclaration des privilège pour l'utilisateur sur la base.
GRANT ALL ON morix.* TO 'morix'@'localhost';

-- Utilisation de la base morix
USE morix;

-- Création de la table des produits
CREATE TABLE Produit(id VARCHAR(255), libelle VARCHAR(255), prix FLOAT, quantite INT);


