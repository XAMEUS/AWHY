from random import randint
from donnees_des_tables import *

#nombre de circuits à creer
N_circuits = 6

#circuits figurant dans la base de données
circuits = []

#liste des lieux à visiter qui ont été générés aléatoirement, pour pouvoir les
# ré-utiliser dans Etapes
lieux_a_visiter = []

#nombre de dates différentes pour un circuit
nb_date_circuit = 20

#nombre de jour maximum d'une étape
nb_jours_max_etape = 3

#nombre maximum d'étapes par circuit
nb_etape_max = 10

#nombre de chambre par hotel
nb_chambre_min = 50
nb_chambre_max = 500

#nombre maximum de lieux à visiter et d'hotel par ville
#pré-condition : doit être inférieur au nombre de lieuxAvisiter qui existent
nb_H_LAV_max = 3

#Maximum de numéro de rue
numero_rue_max = 149

"""
    Créer les circuits
"""
def creer_circuit():
    for i in range(0, N_circuits):
        pays_depart = randint(0,len(villes)-1)
        pays_arrivee = randint(0,len(villes)-1)
        ville_depart = villes[pays_depart][randint(0,len(villes[0])-1)]
        ville_arrivee = villes[pays_arrivee][randint(0,len(villes[0])-1)]
        circuits.append([i+1, ville_depart, pays[pays_depart],
                         ville_arrivee, pays[pays_arrivee]])


"""
    Peuplement de la Relation Ville
"""
def peupler_ville(fichier):
    for idx,cities in enumerate(villes):
        for city in cities:
            fichier.write("insert into Ville VALUES ('" +
                          city + "','" + pays[idx] + "');\n")


"""
    Peuplement de la Relation LieuAvisiter
        15 lieux à visiter par pays
"""
def peupler_LieuAvisiter(fichier):
    lieux_used = [] #pour assurer unicité
    for idx,cities in enumerate(villes):
        for city in cities:
            for i in range(0, randint(1,nb_H_LAV_max)):
                adresse = (str(randint(1,numero_rue_max)) +
                           nom_rue[randint(0,len(nom_rue)-1)] +
                           rue[randint(0,len(rue)-1)])
                lieu = lieux[randint(0,len(lieux)-1)]
                descriptif = dico_description[lieu]
                prix = dico_prix[lieu]

                lieux_used.append(lieu)
                lieux.remove(lieux_used[-1])
                lieux_a_visiter.append([lieu, city, pays[idx]])

                fichier.write("insert into LieuAvisiter VALUES ('" + lieu +
                              "','" + city + "','" + pays[idx] + "','" +
                              adresse + "','" + descriptif + "','" +
                              str(prix) + "');\n")
            for element in lieux_used:
                lieux.append(element)
            del lieux_used[:]


"""
    Peuplement de la Relation Circuit
"""
def peupler_Circuit(fichier, idxCircuit):
    prix_circuit = randint(1,9) * 100
    c = circuits[idxCircuit]
    fichier.write("insert into Circuit VALUES ('"+ str(c[0]) + "','" +
                  descriptif_circuit[randint(0, len(descriptif_circuit)-1)] +
                  "','" + c[1] + "','" + c[2] + "','" + c[3] + "','" + c[4] +
                  "','" + str(c[5]) + "','" + str(prix_circuit) + "');\n")


"""
    Peuplement de la Relation DateCircuit
"""
def peupler_DateCircuit(fichier):
    dates_used = []
    for circuit in circuits:
        for i in range(0, randint(1,nb_date_circuit)):
            moisC = randint(0,11)
            while True :
                dateC = (str(randint(1,mois[moisC][1])) + "-" +
                		str(mois[moisC][0]) + "-2018")
                if dateC not in dates_used:
                    dates_used.append(dateC)
                    break
            #Nombre de personnes n'excède pas 100
            nbPersonnes = randint(10,100)

            fichier.write("insert into DateCircuit VALUES ('"+ str(circuit[0]) +
                          "','" + dateC + "','" + str(nbPersonnes) + "');\n")
        del dates_used[:]

"""
    Peuplement de la Relation Etapes
"""
def peupler_Etapes_et_Circuit(fichier):
    for idx,circuit in enumerate(circuits):
        nb_jours_total_circuit = 0
        nb_jours_des_etapes = []
        lieux_etapes = []
        nb_etapes = 0
        ordre = 1

        nb_etapes = randint(2,nb_etape_max)
        for ordre in range(0, nb_etapes):
            nb_jours_des_etapes.append(randint(0,nb_jours_max_etape))
        nb_jours_total_circuit = sum(nb_jours_des_etapes)
        circuits[idx].append(nb_jours_total_circuit)

        peupler_Circuit(fichier, idx)

        # première (dernière) étapes dans pays de départ (arrivée) du circuit
        for loop in range(0,2):
            while True :
                lieu_etape = lieux_a_visiter[randint(0,len(lieux_a_visiter)-1)]
                if lieu_etape[1] == circuit[[1,3][loop==1]]:
                    lieux_etapes.append(lieu_etape)
                    break

        for etape in range(0, nb_etapes):
            if etape == 0:
                lieu_etape = lieux_etapes[0]
            elif etape == nb_etapes-1:
                lieu_etape = lieux_etapes[1]
            else:
                lieu_etape = lieux_a_visiter[randint(0,len(lieux_a_visiter)-1)]
            nbJours = nb_jours_des_etapes[etape]
            fichier.write("insert into Etapes VALUES ('" + str(circuit[0]) +
		                  "','" + str(etape+1) + "','" + lieu_etape[0] + "','" +
		                  lieu_etape[1] + "','" + lieu_etape[2] + "','" +
		                  str(nbJours) + "');\n")


"""
    Peuplement de la Relation Hotel
"""
def peupler_Hotel(fichier):
    hotels_used = [] #pour garantir unicité des hotels dans les villes
    for idx,cities in enumerate(villes):
        for city in cities:
            for i in range(0, randint(1,nb_H_LAV_max)):
                adresse_hotel = (str(randint(1,numero_rue_max)) +
                           nom_rue[randint(0,len(nom_rue)-1)] +
                           rue[randint(0,len(rue)-1)])
                nom_hotel = hotels[randint(0,len(hotels)-1)]
                nb_chambre_total = randint(nb_chambre_min, nb_chambre_max)
                prix_chambre = randint(10, 150)
                prix_dej = randint(5,50)

                hotels_used.append(nom_hotel)
                hotels.remove(nom_hotel)

                fichier.write("insert into Hotel VALUES ('" + nom_hotel +
                              "','" + city + "','" + pays[idx] + "','" +
                              adresse_hotel + "','" + str(nb_chambre_total) +
                              "','" + str(prix_chambre) + "','" +
                              str(prix_dej) + "');\n")
            for h in hotels_used:
                hotels.append(h)
            del hotels_used[:]
