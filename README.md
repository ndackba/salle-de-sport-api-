## Lancer avec Docker

### Prérequis

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) installé et démarré

### Configuration
Prérequis : Java 21
Crée un fichier `.env` à la racine du projet (à ne jamais versionner) :

```env
MYSQL_DATABASE=salle_de_sport
MYSQL_USER=sallesport_user
MYSQL_PASSWORD=change_moi
MYSQL_ROOT_PASSWORD=change_moi_aussi
```

### Démarrage

```bash
docker compose up --build
```

Cette commande :
- Construit l'image de l'API en **multi-stage build** (compilation Maven puis image finale légère basée sur un JRE Alpine)
- Démarre un conteneur **MySQL 8** avec les identifiants définis dans `.env`
- Démarre l'API, qui attend que MySQL soit prêt (`healthcheck`) avant de se lancer
- Persiste les données MySQL dans un **volume Docker** (`mysql_data`), qui survit à un redémarrage des conteneurs

### Accès

- API : [http://localhost:8080](http://localhost:8080)
- Documentation Swagger : [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Arrêt

```bash
docker compose down
```

Pour arrêter **et supprimer les données** (repartir de zéro) :

```bash
docker compose down -v
```