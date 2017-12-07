
--placesDisponiblesCircuit

-- Toutes les réservations de circuit payées
select * from ReserveCircuit rc
 where exists (select * from Reservation r where r.numDossier = rc.numDossier);

-- Toutes les réservations payées pour 1 circuit à une date
select * from ReserveCircuit rc
 where exists (select * from Reservation r where r.numDossier = rc.numDossier)
 and rc.idCircuit = 'idCircuit' and rc.dateDepartCircuit = 'date';

-- places dispo pour 1 Circuit à une date
select (dc.nbPersonnes - sum(rcd.nbPersonnesCircuit)) as nbPlaces
 from DateCircuit dc,
     (select * from ReserveCircuit rc
     where exists (select * from Reservation r where r.numDossier = rc.numDossier)
     and rc.idCircuit = 'idCircuit' and rc.dateDepartCircuit = 'date') rcd
 where dc.idCircuit = rcd.idCircuit and dc.dateDepartCircuit = rcd.dateDepartCircuit
 group by dc.idCircuit, dc.dateDepartCircuit, dc.nbPersonnes;

-- Liste des Circuits/dates avec des places dispos

-- A faire en JAVA, je galère trop

-- select * from Circuit c,
--     (select *, (dc.nbPersonnes - sum(rc.nbPersonnesCircuit)) as nbPlaces
--      from DateCircuit dc,
--          (select * from ReserveCircuit rc
--          where exists (select * from Reservation r where r.numDossier = rc.numDossier)
--          and rc.idCircuit = 'idCircuit' and rc.dateDepartCircuit = 'date') rc
--      where dc.idCircuit = rc.idCircuit and dc.dateDepartCircuit = rc.dateDepartCircuit
--      group by dc.idCircuit, dc.dateDepartCircuit, dc.nbPersonnes);



--placesDisponiblesHotels

--toutes les réservations d'hotels payées entre 2 dates.
select * from ReserveHotel rh
 where exists (select * from Reservation r where r.numDossier = rh.numDossier)
 and (dateDepartHotel <= 'arrivee' and dateArriveeHotel > 'depart');

--Toutes les reservations payées pour 1 hotel à une date
select * from ReserveHotel rh
 where exists (select * from Reservation r where r.numDossier = rh.numDossier)
 and (dateDepartHotel <= 'date' and dateArriveeHotel > 'date')
 and rh.nomHotel = 'nomHotel' and rh.ville = 'ville' and rh.pays = 'pays';

--nb chambres disponibles pour 1 hotel à une date
select (h.nbChambresTotal - sum(rhd.nbChambresReservees)) as nbPlaces
 from Hotel h,
     (select * from ReserveHotel rh
      where exists (select * from Reservation r where r.numDossier = rh.numDossier)
      and (dateDepartHotel <= 'date' and dateArriveeHotel > 'date')
      and rh.nomHotel = 'nomHotel' and rh.ville = 'ville' and rh.pays = 'pays') rhd
 where h.nomHotel = 'nomHotel' and h.ville = 'ville' and h.pays = 'pays'
 group by h.nomHotel, h.ville, h.pays, h.nbChambresTotal;

-- listes des hotels dispo dates, villes, pays connus

--toutes les réservations d'hotels payées à une certaine date
select * from ReserveHotel rh
 where exists (select * from Reservation r where r.numDossier = rh.numDossier);







    --CLIENT
    -- se connecter avec ses informations client =>JAVA
    -- modifier ses données clients
    --adresseClient
    update Client
    set adresseClient = 'adresseClient'
    where idClient = 'idClient';
    --Mail
    update Client
    set emailClient = 'email'
    where idClient = 'idClient';
    --telephone
    update Client
    set telClient = 'telClient'
    where idClient = 'idClient';


    -- se connecter avec ses informations client
    -- OU enregistrement (nom, prénom, email, tel, type, adresse) si nouveau client
    insert into Client values ('idClient', 'nomClient', 'prenomClient', 'typeClient', 'adresseClient', 'emailClient', 'telClient', 'anneeEnregistrement'); --annee recuperee par app


    -- parcourir le catalogue [pays, villes, hôtels, circuits, lieux]

    -- Liste des circuits disponibles avec leurs prix et les dates
    Select * from Circuit, DateCircuit
    where(nbPersonnes - sum(select nbPersonnesCircuit from (Circuit c join DateCircuit dc join Reservation r) on (c.idCircuit = dc.idCircuit and dc.numDossier = r.numDossier)) > 0);


    -- Liste des hotels dispo par nb chambres voulants etre reservees


    -- créer des réservations [hôtels, circuits, lieux] aux dates voulues
    insert into ReserveHotel values('nomHotel', 'ville', 'pays', 'numDossier', 'dateDepartHotel', 'dateArriveeHotel', 'nbChambresReservees', 'nbPetitDejReserves');
    insert into ReserveCircuit values('idCircuit', 'dateDepartCircuit', 'numDossier', 'nbPersonnesCircuit');
    insert into ReserveVisite values('nomLieu', 'ville', 'pays', 'numDossier', 'dateVisite', 'nbPersonnesVisite');

    -- supprimer les réservations [hôtels, circuits, lieux] existantes
    delete from ReserveHotel where (nomHotel = 'nomHotel' and ville = 'ville' and pays = 'pays' and numDossier = 'numDossier' and dateDepartHotel = 'date1' and dateArriveeHotel = 'date2');
    delete from ReserveCircuit where (idCircuit = 'idCircuit' and dateDepartCircuit = 'date' and numDossier = 'numDossier');
    delete from ReserveVisite where (nomLieu = 'nomLieu' and ville = 'ville' and pays = 'pays' and numDossier = 'numDossier' and dateVisite = 'date');


    -- prix total => JAVA
    -- prix des lieux à visiter
    Select sum(prix * nbPersonnesVisite)
    from LieuAVisiter LAV join ReserveVisite RV
    on LAV.nomLieu = RV.nomLieu and LAV.ville = RV.ville and LAV.pays = RV.pays
    where numDossier = AnumDossier;

    -- prix des hotels
    Select sum(prixChambre * nbChambresReservees + prixPetitDejeuner * nbPetitDejeunerReserves)
    from Hotels H join ReserveHotel RH
    on H.nomHotel = RH.nomHotel and H.ville = RH.ville and H.pays = RH.pays
    where numDossier = AnumDossier;

    -- prix des circuits
    Select numDossier, sum(prixCircuit*nbPersonnesCircuit)
    from Circuits C join DateCircuit DC join ReserveCircuit RC
    on C.idCircuit = DC.idCircuit and C.idCircuit = RC.idCircuit
    where numDossier = AnumDossier;

    -- dates de début et fin du voyage
    voyage = circuit || hotel
    select min(dateDepartHotel), max(dateArriveeHotel) from ReserveHotel where numDossier = A[numDossier];


    -- nombres de personnes participant au voyage


    https://www.safaribooksonline.com/library/view/oracle-sqlplus-the/1565925785/ch04s08.html
    As soon as SQL*Plus sees that you have begun to type in a SQL command, it stops parsing and accepts whatever text you enter into the buffer

dc.dateDepartCircuit, dc.nbPersonnes) cd
where c.idCircuit = cd.idCircuit;


--toutes les réservations d'hotels payées à une certaine date
select * from ReserveHotel rh
where exists (select * from Reservation r where r.numDossier = rh.numDossier)
and (dateDepartHotel <= 'arrivee' and dateArriveeHotel > 'depart');

--nb chambres disponibles pour 1 hotel à une date

-- listes des hotels dispo dates, villes, pays connus
select
  -----------------------------
  --toutes les réservations d'hotels payées à une certaine date
select * from ReserveHotel rh
where exists (select * from Reservation r where r.numDossier = rh.numDossier);




--placesDisponiblesHotels


--CLIENT
-- se connecter avec ses informations client =>JAVA
-- modifier ses données clients
--adresseClient
update Client
set adresseClient = 'adresseClient'
where idClient = 'idClient';
--Mail
update Client
set emailClient = 'email'
where idClient = 'idClient';
--telephone
update Client
set telClient = 'telClient'
where idClient = 'idClient';


-- se connecter avec ses informations client
-- OU enregistrement (nom, prénom, email, tel, type, adresse) si nouveau client
insert into Client values ('idClient', 'nomClient', 'prenomClient', 'typeClient', 'adresseClient', 'emailClient', 'telClient', 'anneeEnregistrement'); --annee recuperee par app


-- parcourir le catalogue [pays, villes, hôtels, circuits, lieux]

-- Liste des circuits disponibles avec leurs prix et les dates
Select * from Circuit, DateCircuit
where(nbPersonnes - sum(select nbPersonnesCircuit from (Circuit c join DateCircuit dc join Reservation r) on (c.idCircuit = dc.idCircuit and dc.numDossier = r.numDossier)) > 0);


-- Liste des hotels dispo par nb chambres voulants etre reservees


-- créer des réservations [hôtels, circuits, lieux] aux dates voulues
insert into ReserveHotel values('nomHotel', 'ville', 'pays', 'numDossier', 'dateDepartHotel', 'dateArriveeHotel', 'nbChambresReservees', 'nbPetitDejReserves');
insert into ReserveCircuit values('idCircuit', 'dateDepartCircuit', 'numDossier', 'nbPersonnesCircuit');
insert into ReserveVisite values('nomLieu', 'ville', 'pays', 'numDossier', 'dateVisite', 'nbPersonnesVisite');

-- supprimer les réservations [hôtels, circuits, lieux] existantes
delete from ReserveHotel where (nomHotel = 'nomHotel' and ville = 'ville' and pays = 'pays' and numDossier = 'numDossier' and dateDepartHotel = 'date1' and dateArriveeHotel = 'date2');
delete from ReserveCircuit where (idCircuit = 'idCircuit' and dateDepartCircuit = 'date' and numDossier = 'numDossier');
delete from ReserveVisite where (nomLieu = 'nomLieu' and ville = 'ville' and pays = 'pays' and numDossier = 'numDossier' and dateVisite = 'date');


-- prix total => JAVA
-- prix des lieux à visiter
Select sum(prix * nbPersonnesVisite)
from LieuAVisiter LAV join ReserveVisite RV
on LAV.nomLieu = RV.nomLieu and LAV.ville = RV.ville and LAV.pays = RV.pays
where numDossier = AnumDossier;

-- prix des hotels
Select sum(prixChambre * nbChambresReservees + prixPetitDejeuner * nbPetitDejeunerReserves)
from Hotels H join ReserveHotel RH
on H.nomHotel = RH.nomHotel and H.ville = RH.ville and H.pays = RH.pays
where numDossier = AnumDossier;

-- prix des circuits
Select numDossier, sum(prixCircuit*nbPersonnesCircuit)
from Circuits C join DateCircuit DC join ReserveCircuit RC
on C.idCircuit = DC.idCircuit and C.idCircuit = RC.idCircuit
where numDossier = AnumDossier;

-- dates de début et fin du voyage
voyage = circuit || hotel
select min(dateDepartHotel), max(dateArriveeHotel) from ReserveHotel where numDossier = A[numDossier];


-- nombres de personnes participant au voyage


https://www.safaribooksonline.com/library/view/oracle-sqlplus-the/1565925785/ch04s08.html
As soon as SQL*Plus sees that you have begun to type in a SQL command, it stops parsing and accepts whatever text you enter into the buffer
