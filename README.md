# MesNotes API
Backend API REST pour MesNotes - Application de gestion des notes académiques.

## Description
L'application MesNotes permet à l'utilisateur de calculer les notes finales de ses sujets scolaires et de périodes comme bimestre ou semestre, ainsi que calculer les notes nécessaires pour atteindre une certaine note finale, visualiser graphiquement ces infos et les enregistrer sur un compte.

## Technologies
- **Java 21**
- **Spring Boot 4.0.2**
- **PostgreSQL**
- **Maven**
- **JPA/Hibernate**

## État
- [x] Configuration initiale du projet
- [x] Modèle de données et entités
- [x] Services et logique métier 
- [ ] Contrôleurs REST 
- [ ] Déploiement pour le rendu

## Installation
1. Cloner le dépôt 
```bash
git clone https://github.com/YazminMendoza/mesnotes-api.git cd mesnotes-api
```
2. Créer la base de données
```sql
CREATE DATABASE mesnotes_dev;
```
3. Configurer les identifiants
```properties
# application-dev.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mesnotes_dev
spring.datasource.username=votre_nom_d'utilisateur
spring.datasource.password=votre_mot_de_passe
```
4. Exécutez l'application
```bash 
./mvnw spring-boot:run 
```

## Fonctionnalités

- Moyenne de sujet, période et formation
- Calculer des notes pour atteindre une moyenne souhaitée
- Métriques et graphiques
- Générer fichier PDF
- Enregistrer les infos en compte

## Projets associés
- Frontend Web (React) (travail en cours)
- Application Android (travail en cours)

## Auteur
Yazmín Mendoza
- LinkedIn : [Yazmín Mendoza] (www.linkedin.com/in/yazmín-mendoza-282794299)
- E-mail : medyinmv@gmail.com
