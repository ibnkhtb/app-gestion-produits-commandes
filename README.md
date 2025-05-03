# Application de gestion de commandes de produits

## Présentation

Cette application web permet aux utilisateurs de consulter des produits, passer des commandes, et aux administrateurs de suivre l'activité globale. Elle comprend deux espaces distincts : un espace utilisateur et un espace administrateur.

L’application a été générée à l’aide de **JHipster**, permettant une base solide pour le développement back-end et front-end.

---

## Fonctionnalités principales

### Espace utilisateur :
- Création de compte et authentification
- Navigation par catégories de produits
- Visualisation des produits par catégorie
- Mise en avant des produits populaires sur la page d’accueil (Top Produits)
- Commande de produits en ligne

### Espace administrateur :
- Authentification dédiée
- Visualisation de toutes les commandes passées
- Suivi des produits commandés

---

## Technologies utilisées

- **Front-end** : React (généré avec JHipster)
- **Back-end** : Spring Boot (JHipster), JPA/Hibernate
- **Base de données** : H2 (développement)
- **Sécurité** : Spring Security

---

## Lancement de l’application

### Back-end :
1. Ouvrir le projet avec un IDE compatible
2. Lancer le back-end avec :

```bash
./mvnw
