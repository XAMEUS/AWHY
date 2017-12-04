#!/usr/bin/env python3

import dependance_peuplement as module

"""
Programme générant des fichiers sql pour peupler la base de données

    limitation : ° les adresses générées peuvent ne pas être unique
    		 ° La génération de la première [et de la dernière étape]
    		   d'un circuit se fait dans une boucle potentiellement infinie.
    		   (on cherche aléatoirement un lieu à visiter dont la ville
    		    coincide avec la ville de départ [et d'arrivée] du circuit)
"""

def main():
    fichier = open("peuplement.sql", "w")

    module.peupler_ville(fichier)
    module.peupler_LieuAvisiter(fichier)
    module.peupler_Hotel(fichier)
    module.creer_circuit()
    module.peupler_DateCircuit(fichier)
    module.peupler_Etapes(fichier)
    module.peupler_Circuit(fichier)

    fichier.close()

main()
