 
# L'application Haut Les Bas

Ceci est une application pour gèrer les ventes de chausettes, il utilise Maven pour supporter certaines commandes: 
- GET 
- POST
- PUT 
- DELETE

Quand le App.java est lancé, l'application crée deux chausettes et une vente automatiquement, une de ces ventes est à l'intérieur de la vente créée. 
Ils peuvent être enlever en utilisant la commande DELETE, si l'utilisateur ne les veulent pas.

### Modifier l'inventaire des chausettes:
Lien https: ```` http://localhost:8000/inventaire````
Guide pour les commandes:
- GET: 
  - Sans arguments, liste tous les chausettes dans l'inventaire de l'application, ex: ```GET http://localhost:8000/inventaire```
  - Avec le paramètre id, permet de rechercher une paire de chausettes avec leur identifiant, ex: ```GET http://localhost:8000/inventaire?id=1```
  - Avec les paramètres taille et couleur, permet de chercher une paire de chausettes par leur taille et leur couleur, ex: ```GET http://localhost:8000/inventaire?taille=M4&couleur=rouge```
- POST:
  - Permet de ajouter une nouvelle paire de chausettes avec les paramêtres couleur, taille, typeTissu et prix, ex: ```POST http://localhost:8000/inventaire```
avec le corps de requête:
```json
{
    "couleur":"rouge",
    "taille":"M10",
    "typeTissu":"cotton",
    "prix":19
}
```
- PUT:
  - Permet de modifier une paire de chausettes existante avec les mêmes paramêtres que POST, plus l'identifiant de la chausette à modifier, ex: ```PUT http://localhost:8000/inventaire```
avec le corps de requête:
````json
{
    "identifiant":"1",
    "couleur":"rouge",
    "taille":"M10",
    "typeTissu":"cotton",
    "prix":19
}
````
- DELETE:
  - Permet de supprimer une chausette de l'inventaire en donnant son identifiant comme paramêtre id, ex: ```DELETE http://localhost:8000/ventes?id=1```

### Modifier la liste de ventes:
Lien https: http://localhost:8000/ventes
Guide pour les commandes:
- GET:
    - Sans arguments, liste tous les ventes créés, ex: ```GET http://localhost:8000/ventes```
    - Avec le paramètre id, permet de rechercher une vente avec son identifiant, ex: ```GET http://localhost:8000/ventes?id=1```
    - Avec le paramètre date, devrait permettre de chercher une vente par la date créés, mais je n'ai plus de temps à l'implémenter car Json fonctionne bizarrement avec les dates, ex: ```GET http://localhost:8000/ventes?date=2025-10-13```
- POST:
    - Permet de ajouter une nouvelle vente avec le paramètre chausette, qui contient une liste de chausettes, qu'ils soient déja dans l'inventaire ou pas, ex: ```POST http://localhost:8000/ventes```
      avec le corps de requête:
```json
{
  "chausettes":[{
    "identifiant":3,
    "couleur":"bleu",
    "taille":"F9",
    "typeTissu":"polyestre",
    "prix":6
  }]
}
```
une vente créée comme ceci va enlever tout les chausettes dedans de l'inventaire, s'ils y sont.
- DELETE:
    - Permet d'annuler une vente en donnant son identifiant comme paramêtre id, ex: ```DELETE http://localhost:8000/ventes?id=1```
    une vente enlevé de cette facon retournera tout les chausette dedans à l'inventaire.
