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
    for idx,cities in enumerate(villes):
        for city in cities:
            for i in range(0, randint(1,nb_H_LAV_max)):
                adresse = (str(randint(1,numero_rue_max)) +
                           nom_rue[randint(0,len(nom_rue)-1)] +
                           rue[randint(0,len(rue)-1)])
                lieu = lieux[randint(0,len(lieux)-1)]
                descriptif = dico_description[lieu]
                prix = dico_prix[lieu]

                lieux_a_visiter.append([lieu, city, pays[idx]])

                fichier.write("insert into LieuAvisiter VALUES ('" + lieu +
                              "','" + city + "','" + pays[idx] + "','" +
                              adresse + "','" + descriptif + "','" +
                              str(prix) + "');\n")


"""
    Peuplement de la Relation Circuit
"""
def peupler_Circuit(fichier):
    for c in circuits:
        prix_circuit = randint(1,9) * 100
        fichier.write("insert into Circuit VALUES ('"+ str(c[0]) + "','" +
                      descriptif_circuit[randint(0, len(descriptif_circuit)-1)] +
                      "','" + c[1] + "','" + c[2] + "','" + c[3] + "','" + c[4] +
                      "','" + str(c[5]) + "','" + str(prix_circuit) + "');\n")


"""
    Peuplement de la Relation DateCircuit
"""
def peupler_DateCircuit(fichier):
    for circuit in circuits:
        for i in range(0, randint(1,nb_date_circuit)):
            moisC = randint(1,12)
            dateC = "2018-" + str(moisC) + "-" + str(randint(1,mois[moisC]))
            #Nombre de personnes n'excède pas 100
            nbPersonnes = randint(10,100)

            fichier.write("insert into DateCircuit VALUES ('"+ str(circuit[0]) +
                          "','" + dateC + "','" + str(nbPersonnes) + "');\n")


"""
    Peuplement de la Relation Etapes
"""
def peupler_Etapes(fichier):
    for idx,circuit in enumerate(circuits):
        nb_jours_total_circuit = 0
        ordre = 1

        # premiere étape dans pays de départ du circuit
        while True :
            lieu_etape = lieux_a_visiter[randint(0,len(lieux_a_visiter)-1)]
            if lieu_etape[1] == circuit[1]:
                break
        nbJours = randint(0,nb_jours_max_etape)
        nb_jours_total_circuit += nbJours
        fichier.write("insert into Etapes VALUES ('" + str(circuit[0]) +
                      "','" + str(1) + "','" + lieu_etape[0] + "','" +
                      lieu_etape[1] + "','" + lieu_etape[2] + "','" +
                      str(nbJours) + "');\n")

        for ordre in range(2, randint(2,nb_etape_max)):
            lieu_etape = lieux_a_visiter[randint(0,len(lieux_a_visiter)-1)]
            nbJours = randint(0,nb_jours_max_etape)
            nb_jours_total_circuit += nbJours
            fichier.write("insert into Etapes VALUES ('" + str(circuit[0]) +
                          "','" + str(ordre) + "','" + lieu_etape[0] + "','" +
                          lieu_etape[1] + "','" + lieu_etape[2] + "','" +
                          str(nbJours) + "');\n")

        # dernière étape dans le pays d'arrivé du circuit
        while True :
            lieu_etape = lieux_a_visiter[randint(0,len(lieux_a_visiter)-1)]
            if lieu_etape[1] == circuit[3]:
                break
        nbJours = randint(0,nb_jours_max_etape)
        nb_jours_total_circuit += nbJours
        fichier.write("insert into Etapes VALUES ('" + str(circuit[0]) +
                      "','" + str(ordre+1) + "','" + lieu_etape[0] + "','" +
                      lieu_etape[1] + "','" + lieu_etape[2] + "','" +
                      str(nbJours) + "');\n")

        circuits[idx].append(nb_jours_total_circuit)


"""
    Peuplement de la Relation Hotel
"""
def peupler_Hotel(fichier):
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
                prix_dej = ["NULL", prix_dej][prix_dej >= 10]

                fichier.write("insert into Hotel VALUES ('" + nom_hotel +
                              "','" + city + "','" + pays[idx] + "','" +
                              adresse_hotel + "','" + str(nb_chambre_total) +
                              "','" + str(prix_dej)+"');\n")
