BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE ReserveHotel';
EXCEPTION
    WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE ReserveVisite';
EXCEPTION
    WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE ReserveCircuit';
EXCEPTION
    WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Paiement';
EXCEPTION
    WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Simulation';
EXCEPTION
    WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Client';
EXCEPTION
    WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Hotel';
EXCEPTION
    WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE sequence_name';
EXCEPTION
    WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/


BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Etapes';
EXCEPTION
    WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE DateCircuit';
EXCEPTION
    WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Circuit';
EXCEPTION
    WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE LieuAvisiter';
EXCEPTION
    WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Ville';
EXCEPTION
    WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

create table Ville(
    nomVille char(20) NOT NULL,
    pays char(20) NOT NULL,
    PRIMARY KEY (nomVille, pays)
);

create table LieuAvisiter(
    nomLieu char(20) NOT NULL,
    ville char(20) NOT NULL,
    pays char(20) NOT NULL,
    adresseLieu varchar(50) NOT NULL,
    descriptifLieu varchar(100) NOT NULL,
    prix integer NOT NULL check(prix >= 0),
    PRIMARY KEY (nomLieu, ville, pays),
    FOREIGN KEY (ville, pays) references Ville(nomVille, pays)
);

create table Circuit(
    idCircuit char(5) NOT NULL PRIMARY KEY,
    descriptif char(50) NOT NULL,
    villeDepart char(20) NOT NULL,
    paysDepart char(20) NOT NULL,Avec les séquences il faut faire dans la même transaction un NEXTVAL, puis CURRVAL à chaque fois que la valeur utilisée par la requête ayant utilisé le NEXTVAL est nécessaire.
    villeArrivee char(20) NOT NULL,
    paysArrivee char(20) NOT NULL,
    nbJoursTotal integer NOT NULL check(nbJoursTotal > 0),
    prixCircuit integer NOT NULL check(prixCircuit > 0),
    FOREIGN KEY (villeDepart, paysDepart) references Ville(nomVille, pays),
    FOREIGN KEY (villeArrivee, paysArrivee) references Ville(nomVille, pays)
);

create table DateCircuit(
    idCircuit char(5) NOT NULL,
    dateDepartCircuit date NOT NULL,
    nbPersonnes integer NOT NULL check(nbPersonnes > 0),
    PRIMARY KEY (idCircuit, dateDepartCircuit),
    FOREIGN KEY (idCircuit) references Circuit(idCircuit)
);

create table Etapes(
    idCircuit char(5) NOT NULL,
    ordre integer NOT NULL check(ordre > 0),
    nomLieu char(20) NOT NULL,
    ville char(20) NOT NULL,
    pays char(20) NOT NULL,
    nbJours integer NOT NULL check(nbJours >= 0),
    PRIMARY KEY (idCircuit, ordre),
    FOREIGN KEY (idCircuit) references Circuit(idCircuit),
    FOREIGN KEY (nomLieu, ville, pays) references LieuAvisiter(nomLieu, ville, pays)
);

create table Hotel(
    nomHotel char(20) NOT NULL,
    ville char(20) NOT NULL,
    pays char(20) NOT NULL,
    adresseHotel char(50) NOT NULL,
    nbChambresTotal integer NOT NULL check(nbChambresTotal > 0),
    prixChambre integer NOT NULL,
    prixPetitDejeuner integer check((prixPetitDejeuner IS NULL) OR (prixPetitDejeuner > 0)),
    PRIMARY KEY (nomHotel, ville, pays),
    FOREIGN KEY (ville, pays) references Ville(nomVille, pays)
);

create table Client(
    idClient integer NOT NULL PRIMARY KEY,
    nomClient char(20) NOT NULL,
    prenomClient char(20) NOT NULL,
    typeClient char NOT NULL check(typeClient in ('société', 'groupe', 'individuel')),
    adresseClient varchar(100) NOT NULL,
    emailClient char(50) NOT NULL,
    telClient char(20) NOT NULL,
    anneeEnregistrement integer NOT NULL
);

CREATE SEQUENCE numDossier MINVALUE 0 INCREMENT BY 1 NOCACHE;

create table Simulation(
    numDossier integer NOT NULL PRIMARY KEY AUTO_INCREMENT
);

create table Reservation(
    numDossier integer NOT NULL,
    idClient integer NOT NULL,
    datePaiement date NOT NULL,
    infoPaiement varchar(1000),
    PRIMARY KEY (numDossier, idClient),
    FOREIGN KEY (numDossier) references Simulation(numDossier)
    FOREIGN KEY (idClient) references Client(idClient)
);

create table ReserveCircuit(
    idCircuit char(5) NOT NULL,
    dateDepartCircuit date NOT NULL,
    numDossier integer NOT NULL,
    nbPersonnesCircuit integer NOT NULL check(nbPersonnesCircuit > 0),
    PRIMARY KEY (idCircuit, dateDepartCircuit, numDossier),
    FOREIGN KEY (idCircuit, dateDepartCircuit) references DateCircuit(idCircuit, dateDepartCircuit),
    FOREIGN KEY (numDossier) references Simulation(numDossier)
);

create table ReserveHotel(
    nomHotel char(20) NOT NULL,
    ville char(20) NOT NULL,
    pays char(20) NOT NULL,
    numDossier integer NOT NULL,
    dateDepartHotel date NOT NULL,
    dateArriveeHotel date NOT NULL,
    nbChambresReservees integer NOT NULL check(nbChambresReservees > 0),
    nbPetitDejReserves integer NOT NULL check(nbPetitDejReserves >= 0),
    PRIMARY KEY (numDossier, nomHotel, ville, pays, dateDepartHotel, dateArriveeHotel),
    FOREIGN KEY (nomHotel, ville, pays) references Hotel(nomHotel, ville, pays),
    FOREIGN KEY (numDossier) references Simulation(numDossier),
    check(dateDepartHotel <= dateArriveeHotel)
);

create table ReserveVisite(
    nomLieu char(20) NOT NULL,
    ville char(20) NOT NULL,
    pays char(20) NOT NULL,
    numDossier integer NOT NULL,
    dateVisite date NOT NULL,
    nbPersonnesVisite integer NOT NULL check(nbPersonnesVisite > 0),
    PRIMARY KEY (nomLieu, ville, pays, numDossier, dateVisite),
    FOREIGN KEY (nomLieu, ville, pays) references LieuAvisiter(nomLieu, ville, pays),
    FOREIGN KEY (numDossier) references Simulation(numDossier)
);
