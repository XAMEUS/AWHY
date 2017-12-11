Liste des requetes SQL utilisées dans le code JAVA.

--Créé la prochaine valeur de idClient. (création du client)
"select idclient.nextval from client"

--Créé la prochaine valeur de numDossier. (création de la simulation)
"select numdossier.nextval from simulation"

--Affiche la simulation du dossier numero <numDossier>. (création de la simulation)
"select * from Simulation where numDossier= (?)"



--Affiche la liste des hotels dans la ville sélectionnée. (Démarrer une simulation)
"select * from hotel where ville LIKE ? and pays LIKE ?"

--Affiche la liste des lieux à visiter dans la ville sélectionnée. (Démarrer une simulation)
"select * from lieuavisiter where ville LIKE ? and pays LIKE ?"

--Affiche la liste des circuits démarrant dans la ville sélectionnée. (Démarrer une simulation)
"select * from Circuit where villeDepart LIKE ? and paysDepart LIKE ?"

--Affiche toutes les simulations, payées ou non. (Démarrer une simulation)
"select * from Simulation"



--Affiche la liste des étapes d'un circuit. (Faire une simulation pour un circuit)
"select * from etapes where idCircuit = " + idCircuit

--Affiche la liste des dates et le nombre de places maximal associé d'un circuit.(Faire une simulation pour un circuit)
"select * from dateCircuit where idCircuit = " + idCircuit

--Affiche la liste des étapes ordonnées d'un circuit. (Faire une simulation pour un circuit)
"select * from etapes where idCircuit = " + idCircuit + " order by ordre"

--Affiche la liste des hotels d'une ville. (Faire une simulation pour un circuit)
--(pour chaque étape d'un circuit)
"select * from hotel where ville='" + ville + "' AND pays='" + pays +"'"



--Affiche les details d'une simulation. (Recapitulatif d'une reservation)
"SELECT * FROM Simulation WHERE numDossier=?"

--Recupère les dates de début et de fin de la simulation. (Recapitulatif d'une reservation)
--(Comparaison des dates extremales en JAVA)
"SELECT min(dateDepartHotel), max(dateArriveeHotel) FROM ReserveHotel WHERE numDossier=?"
"SELECT min(dateDepartCircuit), max(enbJoursTotal + dateDepartCircuit) FROM ReserveCircuit R, Circuit C WHERE numDossier=? and R.idCircuit=C.idCircuit"
"SELECT min(dateVisite), max(dateVisite) FROM ReserveVisite R WHERE numDossier=?"

--Récupère le nombre de personnes maximales de la simulation.(Recapitulatif d'une reservation)
--(Comparaison des résultats en JAVA)
"SELECT max(nbPersonnesVisite) FROM ReserveVisite WHERE numDossier=?"
"SELECT max(nbChambresReservees) FROM ReserveHotel WHERE numDossier=?"
"SELECT max(nbPersonnesCircuit) FROM ReserveCircuit WHERE numDossier=?"

--Récupère le prix de la simulation. (Recapitulatif d'une reservation)
--(Somme des résultats en JAVA)
"SELECT sum(prixCircuit) FROM ReserveCircuit R, Circuit C WHERE numDossier=? and R.idCircuit = C.idCircuit"
"SELECT sum(prixChambre * nbChambresReservees + prixPetitDejeuner * nbPetitDejReserves) FROM ReserveHotel R, Hotel H WHERE numDossier=? and R.nomHotel = H.nomHotel and R.ville = H.ville and R.pays = H.pays"
"SELECT sum(prix) FROM ReserveVisite R, LieuAVisiter L WHERE numDossier=? and R.nomLieu = L.nomLieu and R.ville = L.ville and R.pays = L.pays"

--Vérification que le nombre de place disponible est suffisant pour chaque circuit de la réservation. (Recapitulatif d'une reservation)
--(La vérification que les nombres obtenus sont supérieurs à 0 se fait en JAVA)
"SELECT nbPersonnesCircuit, idCircuit, dateDepartCircuit FROM ReserveCircuit WHERE numDossier=?"
"select (dc.nbPersonnes - sum(rcd.nbPersonnesCircuit)) as nbPlaces
from DateCircuit dc, (
    select idCircuit, dateDepartCircuit, nbPersonnesCircuit
    from ReserveCircuit rc, Reservation r
    where r.numDossier = rc.numDossier and rc.idCircuit = ? and rc.dateDepartCircuit = ?) rcd
where dc.idCircuit = rcd.idCircuit and dc.dateDepartCircuit = rcd.dateDepartCircuit
group by dc.idCircuit, dc.dateDepartCircuit, dc.nbPersonnes"

--Vérification que le nombre de place disponible est suffisant pour chaque hotel de la réservation. (Recapitulatif d'une reservation)
--(La vérification que les nombres obtenus sont supérieurs à 0 se fait en JAVA)
"SELECT nomHotel, ville, pays, nbChambresReservees, dateDepartHotel, dateArriveeHotel FROM ReserveHotel WHERE numDossier=?"
"select (h.nbChambresTotal - sum(rhr.nbChambresReservees))
from (select nomHotel, ville, pays, nbChambresTotal from Hotel) h, (
    select rh.nomHotel, rh.ville, rh.pays, rh.nbChambresReservees
    from ReserveHotel rh, Reservation r
    where rh.numDossier = r.numDossier and rh.nomHotel = ? and rh.ville = ? and rh.pays = ? and (dateDepartHotel <= ? and dateArriveeHotel > ?)) rhr
where h.nomHotel = rhr.nomHotel and h.ville = rhr.ville and h.pays = rhr.pays
group by h.nomHotel, h.ville, h.pays, h.nbChambresTotal"

--Affiche les circuits de la reservation. (Recapitulatif d'une reservation)
"select * from reservecircuit where numDossier = '" + numDossier + "'"

--Affiche les hotels de la reservation. (Recapitulatif d'une reservation)
"select * from reservehotel where numDossier = '" + numDossier + "'"

--Affiche les lieux à visiter de la reservation. (Recapitulatif d'une reservation)
"select * from reservevisite where numDossier = '" + numDossier + "'"

--Affiche les informations de paiement et la date de paiement d'une Reservation. (Recapitulatif d'une reservation)
"SELECT * FROM Reservation WHERE numDossier=?"



--Affiche tout les éléments d'une table.
"select * from "+<nomTable>

--Affiche tout les éléments d'une reservation.
"SELECT rv.* FROM ReserveVisite rv, Reservation r WHERE rv.numDossier = r.numDossier"
"SELECT rh.* FROM ReserveHotel rh, Reservation r WHERE rh.numDossier = r.numDossier"
"SELECT rc.* FROM ReserveCircuit rc, Reservation r WHERE rc.numDossier = r.numDossier"
