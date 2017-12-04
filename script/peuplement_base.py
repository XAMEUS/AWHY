#!/usr/bin/env python3

import dependance_peuplement as module

"""
Programme générant des fichiers sql pour peupler la base de données

    limitation : ° les adresses générées peuvent ne pas être unique
    		     ° La génération de la première [et de la dernière étape]
    		     d'un circuit se fait dans une boucle potentiellement infinie.
    		     (on cherche aléatoirement un lieu à visiter dont la ville
    		      coincide avec la ville de départ [et d'arrivée] du circuit)
                 ° La création des dates de circuit est aléatoire et perdure
                 tant qu'une date non utilisée n'est pas trouvée.
                 (risque de boucle infinie)
"""

def main():
    fichier = open("peuplement.sql", "w")

    module.peupler_ville(fichier)
    module.peupler_LieuAvisiter(fichier)
    module.peupler_Hotel(fichier)
    module.creer_circuit()
    module.peupler_Etapes_et_Circuit(fichier)
    module.peupler_DateCircuit(fichier)

    fichier.close()

main()
