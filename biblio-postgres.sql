------------------------------------------
-- Exemple de la bibliotheque
-- Marc Frappier, Universite de Sherbrooke
-- 2000-01-25
-- Modifié par Vincent Ducharme - 2018
------------------------------------------

DROP TABLE membre CASCADE;
CREATE TABLE membre (
utilisateur     varchar(255) NOT NULL,
motDePasse      varchar(255) NOT NULL,
acces           numeric(1) NOT NULL DEFAULT 1,
nom             varchar(10) NOT NULL,
telephone       numeric(10),
limitePret      numeric(2) check(limitePret > 0 and limitePret <= 10) ,
nbpret          numeric(2) default 0 check(nbpret >= 0) ,
PRIMARY KEY (utilisateur),
check(nbpret <= limitePret));

DROP TABLE livre CASCADE;
CREATE TABLE livre (
idLivre         numeric(3) check(idLivre > 0) ,
titre           varchar(10) NOT NULL,
auteur          varchar(10) NOT NULL,
dateAcquisition date not null,
utilisateur     varchar(255) ,
datePret        date ,
PRIMARY KEY (idLivre),
FOREIGN KEY (utilisateur) REFERENCES membre
);

DROP TABLE reservation CASCADE;
CREATE TABLE reservation (
idReservation   numeric(3) ,
utilisateur     varchar(255) ,
idLivre         numeric(3) ,
dateReservation date ,
PRIMARY KEY (idReservation) ,
UNIQUE (utilisateur,idLivre) ,
FOREIGN KEY (utilisateur) REFERENCES membre,
FOREIGN KEY (idLivre) REFERENCES livre
);

insert into membre(utilisateur, motDePasse, acces, nom, telephone, limitepret, nbpret) values('admin', 'test', 0, 'admin', 1234567892, 5, 0);