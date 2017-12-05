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
    paysDepart char(20) NOT NULL,
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
    prixPetitDejeuner integer NOT NULL check(prixPetitDejeuner > 0),
    PRIMARY KEY (nomHotel, ville, pays),
    FOREIGN KEY (ville, pays) references Ville(nomVille, pays)
);
