# Pet-Service

A simple project to study some kubernetes features using a spring-boot application and postgres.

For this propose, it will be created a deployment with some pods and avoiding a lock database caused by the Liquibase
framework. It happens because liquibase inside the application start migrations tasks and the first one is lock our
database, in parallel others pods starts the same steps and could throw an exception or falling through the deployment
error.

Here the focus is on the architecture, so if you see the application code, you will see a simples api.

Look in the resource folder, all the migration files, fell free to create yours or modifying for tests.

## Tech Stack

**Built-in:**

* [Maven](https://maven.apache.org/)
* [Spring](https://spring.io/)
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

create a `.env` file in root of project. you can create using the example file `.env.sample`

in `.env` you need to set your values.

All properties will be read in `docker-compose` file.

## Development

### Docker-Compose (Postgres)

Adjust `docker-compose` file.

Change in line 9 and set where _Postgres_ will create the database.

```yaml
volumes:
  - ~/workspace/pgdata:/var/lib/postgresql/data
```

> **Optional** - If you do not want to persist data, just comment both lines.
>
> ⚠️ But every time you stop and start the image, the database will be empty.

Other service in `docker-compose` is _adminer_. It is a simple web database client.

![an image showing the login the page of adminer](img/adminer-login-page.png "adminer-login-page")

TODO: More details and instructions

## Running

Compile the app

```bash
mvn clean install
```

build app image

```bash
mvn spring-boot:build-image -Dspring-boot.build-image.imageName=leoyassuda/pet-service
```

in parameter `imageName` set your name image.

Build liquibase image

```bash
docker build -f LiquibaseDockerfile -t leoyassuda/liquibase:latest .
```

Start Postgres

```bash
docker-compose up
```

Apply kubernetes deployment

> Check some values in `deployment-app.yaml`

In lines 31 and 48, set the same images name that was built previously.

If you pushed the images to docker hub, may you need to set the context for kubernetes can pull images.

First login

```bash
docker login
```

Then pass context to kubernetes

```bash
kubectl create secret generic regcred \
    --from-file=.dockerconfigjson=<path/to/.docker/config.json> \
    --type=kubernetes.io/dockerconfigjson
```

Start the deployment

```bash
kubectl apply -f templates/deployment-app.yaml
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

mvn spring-boot:build-image -Dspring-boot.build-image.imageName=leoyassuda/pet-service

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
kubectl apply -f templates/deployment-app.yaml
```

#### kube restart deployment

```bash
kubectl rollout restart deployment pet-app
```

#### build liquibase image

```bash
docker build -f LiquibaseDockerfile -t liquibase:latest .
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

## References

TODO: Insert and describe links references here

---

## Authors

- **Leo Yassuda** - _Initial work_ - [Portfolio](https://leoyas.com)
