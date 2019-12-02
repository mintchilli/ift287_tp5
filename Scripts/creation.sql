CREATE TABLE Membre(
  noMembre SERIAL PRIMARY KEY,
  nom varchar,
  prenom varchar,
  nomUtilisateur varchar,
  motDePasse varchar,
  estAdmin boolean DEFAULT false
  );
  
INSERT INTO Membre(nom, prenom, nomUtilisateur, motDePasse, estAdmin)
VALUES
	('admin', 'admin', 'admin', 'admin', true);
  
  CREATE TABLE Lot(
  idLot SERIAL PRIMARY KEY,
  nomLot varchar,
  noMaxMembre integer
  );
  
  CREATE TABLE Plante(
  idPlante SERIAL PRIMARY KEY,
  nomPlante varchar,
  tempsCulture integer
  );
  
  CREATE TABLE MembreLot(
  idLot integer REFERENCES Lot(idLot) ON DELETE CASCADE,
  noMembre integer REFERENCES Membre(noMembre) ON DELETE CASCADE,
  validationAdmin boolean DEFAULT false
  );

  CREATE TABLE PlanteLot(
  idLot integer REFERENCES Lot(idLot) ON DELETE CASCADE,
  idPlante integer REFERENCES Plante(idPlante) ON DELETE CASCADE,
  datePlantation date,
  nbExemplaires integer,
  dateDeRecoltePrevu date
  );
