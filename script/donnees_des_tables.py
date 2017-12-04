"""
-------------------------------------------------------------------------------
                Données utilisées pour la génération aléatoire
-------------------------------------------------------------------------------
"""


#villes figurant dans la base de données
#pré-condition : - ne doit pas être vide
#                - les sous-liste comportent des villes d'un même pays
#		 - les sous-listes comportent les villes des pays situés dans
#		   "pays", dans le même ordre
villes = [["New York", "Las vegas", "Atlanta", "Miami", "Springfield", "Bend"],
         ["Paris", "Marseille", "Toulouse", "Lyon", "Grenoble", "Bordeaux"],
         ["Madrid", "Barcelone", "Valence", "Tarragone", "Bilbao", "Seville"],
         ["Helsinki", "Turku", "Tampere", "Vaasa", "Kuopio", "Oulu"],
         ["Sydney", "Melbourne", "Perth", "Brisbane", "Cairns", "Coral Bay"],
         ["Londres", "Cardiff", "Manchester", "Bristol", "Plymouth", "Oxford"],
         ["Rome", "Venise", "Naples", "Milan", "Turin", "Bari"]]

#pays figurant dans la base de données
pays = ["Etats unis", "France", "Espagne", "Finlande", "Australie",
        "Angleterre", "Italie"]

#lieux à visiter figurant dans la base de données
lieux = ["musée Histoire", "musée peintures", "musée sculptures",
         "une belle statue", "une ruine", "un jardin fleuri", "centre ville",
         "stadium", "parc naturel", "vieille batisse", "haut bulding",
         "chateau fort", "chateau pas fort", "cirque", "reserve naturelle"]

#Descriptif des lieux à visiter
dico_description = {"musée Histoire":"musée de renommée mondiale permettant de\
 diversifier ses connaissances historiques","musée peintures":"Présentation de\
 diverses oeuvres artistiques parmi les plus connues du pays",
"musée sculptures":"Présentation de sculptures parmi les plus\
 connues du pays", "une belle statue":"Statue de personnage historique",
"une ruine":"vestige datant de la période de suprématie Romaine",
"un jardin fleuri":"Jardin de renommée mondiale. Sa splendeur rivalise avec la\
 beauté des cieux", "centre ville":"centre culturel et historique de la ville",
"stadium":"Edifice spectaculaire témoignant un engouement sportif populaire",
"parc naturel":"Profitez de cet authentique espace naturel, préservé de toute\
 influence humaine !", "vieille batisse":"Demeure ancestrale, ayant jouer un\
 rôle historique. Nous vous laissons découvrir pourquoi", "haut bulding":"\
 Edifice moderne, prouesse architectural du pays", "chateau fort":"Pont-levis,\
 douves et meurtrières, entrez dans un univers médiéval",
"chateau pas fort":"un chateau on ne peut plus quelconque. Allez le visiter !",
"cirque":"cratère causé par une météorite rarissime! Très impressionant",
"reserve naturelle":"Confrontez vous à la faune et la flaure unique du pays"}

#Descriptif des circuits
descriptif_circuit = ["Un voyage dépaysant ! Découvrez des lieux uniques",
"Oubliez votre quotidien, echapez-vous avec nous !",
"Créez-vous des souvenirs impérissables !",
"Ne rêvez plus, vivez le maintenant !",
"Une évasion spirituelle bénéfique !",
"Suivez-nous dans cette aventure exceptionnelle !",
"Un très mauvais rapport qualité/prix !",
"Tous les retours sont unanimes : <sans interêt> !"]

#rues composant les adresses de lieux à visiter et des hotels
rue = ["Florin", "Azare", "Kiloa", "Ahomet", "Kin Jan un", "Galileo", "Yarma"
       "Pilian", "Florentin", "Caillaoue", "Paris", "Newton", "Einstein",
       "Rene Cotty", "De la 1ere Guerre Mondiale", "De la 2eme Guerre Mondiale",
       "De la montagne", "Du lac", "Des Lilas", "Des associations ternaires",
       "De la Physique", "Des Mathématiques", "De la littérature", "Okarin",
       "De la conquete spatiale", "Bobineau Christophe", "Ahlouche Maxence",
       "Bsaybes Sahar", "Cinar Yagmur Gizem", "Idani Akram",
       "Collet Christine", "Des embrumes", "De la paix dans le monde"]

#Denommage des allées
nom_rue = [" rue ", " boulevard ", " avenue ", " route ", " chemin "]

#Dictionnaire des prix des lieux à visiter
dico_prix = {"musée Histoire":5, "musée peintures":6, "musée sculptures":3,
"une belle statue":0, "une ruine":0, "un jardin fleuri":6, "centre ville":0,
"stadium":10, "parc naturel":0, "vieille batisse":7, "haut bulding":0,
"chateau fort":20, "chateau pas fort":30, "cirque":5, "reserve naturelle":12}

#hotels de la base de données
hotels = ["Sofitel", "Ibis", "Campanile", "Premiere classe", "hotel F1",
"Kyriad", "Mercure", "B&B Hôtels", "Balladins", "Novotel", "Logis Hotels",
"SEH", "Best Western", "Contact Hôtel", "Citôtel"]

#Nombre de jours par mois
mois = [["JAN",31],["FEV",28],["MAR",31],["APR",30],["MAY",31],["JUN",30],["JUL",31],["AUG",31],["SEP",30],["OCT",31],["NOV",30],["DEC",31]]
