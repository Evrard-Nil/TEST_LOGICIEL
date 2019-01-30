# Simple Makefile de manipulation des scripts SQL.
#
# @author : Matthias Brun
#

# Localisation du Makefile sur le disque. 
DIR = $(dir $(lastword $(MAKEFILE_LIST)))

# Création et remplissage de la base de données.
all: database stock

# Création de la base de données.
database :
	mysql -u root -p < $(DIR)/database.sql

# Remplissage de la base de données.
stock :
	mysql -u root -p < $(DIR)/stock.sql

# Vidange de la base de données.
clean_stock :
	mysql -u root -p < $(DIR)/stock_clean.sql

# Suppression de la base de données (et de l'utilisateur de la base).
clean_database : 
	mysql -u root -p < $(DIR)/database_clean.sql

# Ré-initialisation de la base de données (vidange, remplissage).
new_stock : clean_stock stock


