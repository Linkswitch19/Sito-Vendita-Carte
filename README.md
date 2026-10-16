<div align="center">

# 🃏 Sito Vendita Carte

**Marketplace web per la compravendita di carte collezionabili, sviluppato con Spring Boot e Thymeleaf.**

[![Language](https://img.shields.io/badge/Java-21-orange.svg?logo=java)](https://openjdk.org/projects/jdk/21/)
[![Framework](https://img.shields.io/badge/Spring%20Boot-3.4.4-6DB33F.svg?logo=springboot)](https://spring.io/projects/spring-boot)
[![Template Engine](https://img.shields.io/badge/Thymeleaf-005F0F.svg?logo=thymeleaf)](https://www.thymeleaf.org/)
[![Build](https://img.shields.io/badge/build-Maven-C71A36.svg?logo=apachemaven)](https://maven.apache.org/)
[![Container](https://img.shields.io/badge/container-Docker-2496ED.svg?logo=docker)]()
[![Status](https://img.shields.io/badge/status-completato-brightgreen.svg)]()

</div>

---

## 📑 Indice

- [Panoramica](#-panoramica)
- [Funzionalità](#-funzionalità)
- [Stack tecnologico](#-stack-tecnologico)
- [Architettura](#-architettura)
- [Struttura del progetto](#-struttura-del-progetto)
- [Modello dei dati](#-modello-dei-dati)
- [Requisiti](#-requisiti)
- [Esecuzione](#-esecuzione)
- [Docker](#-docker)
- [Integrazione continua](#-integrazione-continua)
- [Autore](#-autore)

---

## 📖 Panoramica

**Sito Vendita Carte** è un'applicazione web full-stack per la compravendita di carte da collezione (a tema anime), sviluppata per il corso di *WebApp* alla SUPSI. Permette agli utenti registrati di sfogliare, cercare, mettere in vendita e gestire annunci di carte, con un sistema di autenticazione e ruoli differenziati tra utenti e amministratori.

---

## ⚙️ Funzionalità

- 🖼️ **Catalogo carte** con vetrina in homepage e slideshow delle carte in evidenza.
- 🔍 **Ricerca dinamica** per nome, descrizione o autore, con aggiornamento della lista tramite frammenti Thymeleaf (senza ricaricare la pagina).
- ➕ **Inserimento nuovi annunci**, con upload di una o più immagini per carta (fino a 10 MB per file).
- ✏️ **Modifica ed eliminazione** degli annunci esistenti.
- 📄 **Pagina di dettaglio** per ogni singola carta, con galleria immagini e descrizione.
- 🔐 **Autenticazione e autorizzazione** basate su Spring Security: login, logout, registrazione utenti, password cifrate con BCrypt.
- 👤 **Ruoli utente** — `USER` per la navigazione e l'inserimento carte, `ADMIN` per la modifica/eliminazione di qualsiasi annuncio.
- 💾 **Inizializzazione automatica del database** con un utente amministratore e una carta di esempio al primo avvio.

---

## 🧱 Stack tecnologico

| Livello | Tecnologia |
|---|---|
| Linguaggio | Java 21 |
| Framework backend | Spring Boot 3.4.4 (Web, Security, Data JPA) |
| Motore di template | Thymeleaf |
| Frontend | HTML5, CSS3, JavaScript, Bootstrap 5.3 |
| Persistenza | Spring Data JPA — H2 (file-based) in sviluppo, driver MySQL incluso per produzione |
| Sicurezza | Spring Security + BCrypt |
| Build | Maven |
| Containerizzazione | Docker (multi-stage build) |
| CI/CD | GitLab CI con build e push dell'immagine Docker |

---

## 🏗️ Architettura

L'applicazione segue l'architettura standard Spring MVC a livelli:

```
   ┌─────────────────────────────┐
   │   Controller (@Controller)  │  MainController, CardController,
   │                             │  FormController, EditCardController
   └─────────────┬───────────────┘
                 │
   ┌─────────────▼───────────────┐
   │        Service              │  CardService, UserService, DBinit
   └─────────────┬───────────────┘
                 │
   ┌─────────────▼───────────────┐
   │      Repository (JPA)       │  CardRepository, UserRepository
   └─────────────┬───────────────┘
                 │
   ┌─────────────▼───────────────┐
   │        Database             │  H2 (file) / MySQL
   └─────────────────────────────┘
```

L'autenticazione è gestita da `SecurityConfig`, che definisce le regole di accesso (pagine pubbliche vs. autenticate vs. riservate agli admin) e da `CustumUserDetailService` per il caricamento degli utenti.

---

## 🗂 Struttura del progetto

```
Sito-Vendita-Carte/
├── src/main/java/ch/supsi/cardgames/
│   ├── controllers/     → MainController, CardController, FormController, EditCardController
│   ├── models/          → Card, User, CardType, Condition, UserRole
│   ├── repository/      → CardRepository, UserRepository (Spring Data JPA)
│   ├── security/        → CustumUserDetailService
│   ├── service/         → CardService, UserService, DBinit
│   └── config/          → SecurityConfig
│
├── src/main/resources/
│   ├── templates/       → Pagine e frammenti Thymeleaf (index, form, dettaglio carta, navbar...)
│   ├── static/css/       → Fogli di stile per sezione (index, form, registrazione...)
│   ├── static/scripts/   → JS (ricerca dinamica, slideshow, validazione form)
│   ├── static/immagini/  → Immagini caricate e asset grafici
│   └── application.properties
│
├── Dockerfile            → Build multi-stage (Maven → JRE Amazon Corretto)
└── .gitlab-ci.yml         → Pipeline CI/CD
```

---

## 🗃️ Modello dei dati

- **Card** — nome, copyright, data, autore, condizione (`Nuova`, `Come nuova`, `Usata`, `Leggermente danneggiata`, `Danneggiata`), tipo (`ANIME`), descrizione e immagini associate.
- **User** — nome, cognome, username, password (cifrata) e ruolo (`ADMIN` / `USER`).

---

## 📦 Requisiti

- **JDK 21** o superiore
- **Apache Maven**
- Nessun database esterno richiesto in sviluppo (usa H2 su file); driver MySQL pronto per un ambiente di produzione.

---

## ▶️ Esecuzione

```bash
mvn spring-boot:run
```

L'applicazione sarà disponibile su `http://localhost:8080`. Al primo avvio viene creato automaticamente un utente amministratore e una carta di esempio nel database H2 locale (`./db/cards`).

Per generare il jar eseguibile:

```bash
mvn clean package
java -jar target/cardgames-0.0.1-SNAPSHOT.jar
```

---

## 🐳 Docker

Il progetto include un `Dockerfile` multi-stage (build con Maven, runtime su Amazon Corretto 21) per architettura ARM64:

```bash
docker build -t sito-vendita-carte .
docker run -p 8080:8080 sito-vendita-carte
```

---

## 🚀 Integrazione continua

La pipeline **GitLab CI** (`.gitlab-ci.yml`) compila l'immagine Docker multi-architettura tramite `buildx` e la pubblica sul registry del progetto ad ogni push sul branch `main`.

---

## 👤 Autore

Progetto sviluppato da **Andre**, per il corso di *WebApp* alla SUPSI.

<div align="center">

---

Fatto con 🎓 alla SUPSI

</div>
