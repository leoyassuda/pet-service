# Pet-Service

This project aims to study hexagonal architecture and explore some Kubernetes features using a Spring Boot application with PostgreSQL in read/write modes.

### Description
The project includes the creation of a deployment with multiple pods, avoiding database locks caused by the Liquibase framework. Liquibase, when starting within the application, initiates migration tasks, and the first action is to lock the database. Since multiple pods start the same steps in parallel, this can cause exceptions or deployment failures.

The main focus of this project is on the architecture.

### Resources
In the resources folder, you will find all migration files. Feel free to create your own files or modify the existing ones for testing.

## Tech Stack

**Built-in:**

* [Maven](https://maven.apache.org/)
* [Spring](https://spring.io/)
* [Liquibase](https://www.liquibase.com/)
* [SpringDoc-OpenApi](https://springdoc.org/)
* [Docker](https://www.docker.com/)
* [Kubernetes](https://kubernetes.io/)
* [K3D](https://k3d.io/stable/)

## Cloning the repo

```bash
git clone https://github.com/leoyassuda/pet-service.git
```

## Pre-requirements

You will need:

- Docker and Docker-Compose - To build and run the images
- K3D - It's a lightweight kubernetes to run locally
- JDK 11
- Maven 3

## Postgres Setup

This project is based on Docker and Kubernetes.

In `infra` folder exists all files to set up the development environment.

create a `.env.datasource` and `.env.pgadmin` files in infra folder. You can create following the example files `.env`
in `templates` folder.

For kubernetes, open `secret.yaml` and put your credentials replacing both string data values for the same value as you
config in `.env` files.

## Development

### Docker-Compose (Postgres)

There are two services commented:

- adminer
    - a simple visual client to access the database.
    - ![an image showing the login the page of adminer](img/adminer-login-page.png "adminer-login-page")
- pg_admin:
    - the power tool to admin Postgres Instances.
    - ![an image showing the login the page of pg-admin](img/pg-admin-login.png "pg admin login-page")
    - to login, depends on the `env.pgadmin` file.

Both are optional to use.

The `docker-compose` reference two folders `pg-main` and `pg-replica` they are used to building and configure the system
replication between databases.

> ⚠ **IMPORTANT** - Volume is used to replicate from master to replication using async wal config.

## Running

Compile the app

```bash
mvn clean install
```

build app image

```bash
mvn spring-boot:build-image -Dspring-boot.build-image.imageName=leoyassuda/pet-service
```

in parameter `imageName` set your image name.

Build liquibase image

In `infra` folder.

Open `liquibase.properties` and set all values according the main Postgres instance.

```bash
docker build -f liquibase.dockerfile -t leoyassuda/liquibase:latest ../
```

Start Postgres

```bash
docker-compose up
```

Create kubernetes secrets

Set values in `secret.yaml`

Execute command

```shell
kubectl create -f secret.yaml
```

Apply kubernetes deployment

> Check some values in `deployment-app.yaml`

Pay attention in Deployment at `spec.containers.image` the same image name that you built before

If you pushed the images to docker hub, may you need to set the context for kubernetes can pull images.

Other point to set value is the network, after docker-compose up with success. Find the gateway to set in your
deployment.

```shell
docker network inspect <network_name> 
```

Or with JQ installed previous

```shell
docker network inspect infra_bridge-petwork | jq '.[0].IPAM.Config[0].Gateway'
```

> shell print -> "172.26.0.1"

Login in terminal

```bash
docker login
```

Then pass (docker) context to kubernetes

```bash
kubectl create secret generic regcred \
    --from-file=.dockerconfigjson=<path/to/.docker/config.json> \
    --type=kubernetes.io/dockerconfigjson
```

Start the deployment

```bash
kubectl apply -f infra/deployment-app.yaml
```

To follow pods status

```bash
kubectl get pods -w
```

![A watch process to follow pods status](img/pods-watch-app-liquibase-ok.png "Pods status")

See logs

```bash
kubectl logs --tail=100 -f <podName>
```

![The application logs in one of pods](img/pod1-liquibase-off-ok.png "Pod app logs")

To see liquibase logs

```bash
kubectl logs --tail=100 -f <podName> -c liquibase
```

![Liquibase logs showing itself process](img/liquibase-image-ok.png "Liquibase execution logs")

Using a client, check the database:

- Before migration
    - ![Database structure before migration](img/postgres-before-migration.png "Postgres before migration")
- After migration
    - ![Database structure after migration](img/postgres-after-migration-liquibase-ok.png "Postgres after migration")

## API Document

TODO: Describe swagger documentation here

## Utils

Some util commands

#### create image using maven

```shell
mvn spring-boot:build-image -Dspring-boot.build-image.imageName=leoyassuda/pet-service
```

#### push image

```bash
docker push leoyassuda/pet-service
```

#### tail logs pod last 100 lines

```bash
kubectl logs --tail=100 -f <podName>
```

#### apply deployment kubernetes

```bash
kubectl apply -f infra/deployment-app.yaml
```

#### kube restart deployment

```bash
kubectl rollout restart deployment pet-app
```

#### build liquibase image

```bash
docker build -f liquibase.dockerfile -t liquibase:latest .
```

#### set scale

```bash
kubectl scale deployment/pet-app --replicas=0
```

#### create docker config auth

```bash
kubectl create secret generic regcred \
  --from-file=.dockerconfigjson=<path/to/.docker/config.json> \
  --type=kubernetes.io/dockerconfigjson
```

#### inspect using JQ

```bash
docker network inspect infra_bridge-petwork | jq '.[0].IPAM.Config[0].Gateway'
```

#### export DB Ip Connection

```bash
export DB_CONNECTION_IP=$(docker network inspect infra_bridge-petwork | jq -r '.[0].IPAM.Config[0].Gateway')
```

---

## Authors

- **Leo Yassuda** - _Initial work_ - [Portfolio](https://leoyas.com)
