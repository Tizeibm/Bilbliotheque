# ![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=java&logoColor=white) ![SQLite](https://img.shields.io/badge/SQLite-003B57?style=flat-square&logo=sqlite&logoColor=white) ![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat-square&logo=apachemaven&logoColor=white)

# Bibliothèque

Une application Java permettant de gérer les livres, les membres et les emprunts dans une bibliothèque. Ce projet met en œuvre les principes de la programmation orientée objet (POO), une structure claire en couches (DAO, modèle, interface), et utilise une base de données SQLite.

## Fonctionnalités clés

- Gestion des livres : ajout, suppression et consultation des livres disponibles.
- Gestion des membres : ajout, suppression et consultation des membres de la bibliothèque.
- Gestion des emprunts : suivi des emprunts de livres par les membres.
- Interface utilisateur intuitive pour une navigation facile.

## Tech Stack

| Technologie | Description |
|-------------|-------------|
| ![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=java&logoColor=white) | Langage de programmation utilisé pour le développement de l'application. |
| ![SQLite](https://img.shields.io/badge/SQLite-003B57?style=flat-square&logo=sqlite&logoColor=white) | Base de données utilisée pour stocker les informations des livres, membres et emprunts. |
| ![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat-square&logo=apachemaven&logoColor=white) | Outil de gestion de projet et d'automatisation de la construction. |

## Instructions d'installation

### Prérequis

- JDK 11 ou supérieur
- Maven
- SQLite

### Guide d'installation

1. **Clonez le dépôt :**
   ```bash
   git clone https://github.com/Tizeibm/Bilbliotheque.git
   cd Bilbliotheque
   ```

2. **Construisez le projet avec Maven :**
   ```bash
   mvn clean install
   ```

3. **Démarrez l'application :**
   ```bash
   mvn exec:java -Dexec.mainClass="com.POO.App"
   ```

## Utilisation

Une fois l'application démarrée, vous pouvez interagir avec l'interface graphique pour :

- Ajouter, consulter ou supprimer des livres.
- Gérer les membres de la bibliothèque.
- Suivre les emprunts effectués par les membres.

## Structure du projet

Voici un aperçu de la structure du projet :

```
Bilbliotheque/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   ├── POO/
│   │   │   │   │   ├── App.java               # Point d'entrée de l'application
│   │   │   │   │   ├── controller/            # Contrôleurs pour gérer l'interaction entre la vue et le modèle
│   │   │   │   │   ├── db/                     # Classes DAO pour l'accès à la base de données
│   │   │   │   │   ├── model/                  # Modèles représentant les entités de la bibliothèque
│   │   └── resources/
│   │       └── com/
│   │           ├── style.css                   # Styles CSS pour l'interface utilisateur
│   │           ├── *.fxml                      # Fichiers FXML pour la définition des interfaces utilisateur
├── library.db                                   # Base de données SQLite
├── pom.xml                                      # Fichier de configuration Maven
```

### Explication des principaux fichiers et répertoires

- **App.java** : Le point d'entrée de l'application.
- **controller/** : Contient les contrôleurs qui gèrent la logique de l'application.
- **db/** : Contient les classes DAO pour l'accès à la base de données.
- **model/** : Définit les modèles de données pour les livres, membres et emprunts.
- **resources/** : Contient les fichiers de ressources, y compris les fichiers FXML et CSS.

## Contribuer

Les contributions sont les bienvenues ! Pour contribuer, veuillez suivre ces étapes :

1. Forkez le projet.
2. Créez une nouvelle branche (`git checkout -b feature/YourFeature`).
3. Effectuez vos modifications et validez (`git commit -m 'Ajout d'une nouvelle fonctionnalité'`).
4. Poussez vers la branche (`git push origin feature/YourFeature`).
5. Ouvrez une Pull Request.

Nous vous remercions de votre intérêt pour le projet !
