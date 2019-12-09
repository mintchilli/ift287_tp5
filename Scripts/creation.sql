CREATE TABLE Membre(
  noMembre SERIAL primary key,
  nom varchar,
  prenom varchar,
  motDePasse varchar,
  estAdmin boolean DEFAULT false,
  );
  
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
  
  INSERT INTO Membre (nom, prenom, motDePasse, estAdmin)
  VALUES ("admin", "admin", "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918", true);