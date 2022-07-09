# P9-MEDISCREEN

P9-Développez une solution en microservices pour votre client - Murer Erik


## Technologies

- Java 11 JDK
- Gradle 7.4.1
- Spring 2.7.1
- Docker

## Specifications techniques

TourGuide se décompose en 4 microservice :
1. Mediscreen-Central (Gateway) - https://github.com/ErikM06/OC_P9_MEDISCREEN_CENTRAL.git
2. Mediscreen-Patient-Repo (microservice patient) - https://github.com/ErikM06/OC_P9_Mediscreen_RepoCentral.git
3. Mediscreen-Notes-Repo (miscroservice observation) - https://github.com/ErikM06/OC_P9_ObservationRepo.git
4. Mediscreen-DiabetesRisk (miscroservice diabetes risk) - https://github.com/ErikM06/OC_P9_MEDISCREEN_DIABETESRISK.git
5. Mediscreen-Eureka - https://github.com/ErikM06/OC_P9_Mediscreen_Eureka.git

## Lancer l'application

### Gradle :
1. Builder l'application

`$ gradle build̀`

2. Run l'application
`$ gradle bootRun`


### Docker :
1. Builder l'application avec gradle (n° 1 vu ci-dessus).

2. Créer une image docker pour chaque micro-service dans leur dossier racine, a l'aide de la commande suivante :
`$ docker build -t NAME_OF_YOUR_IMAGE:TAGVERSION .` 

3. Dans le dossier racine de Mediscreen-Central, utiliser Docker-Compose pour assembler les images et les lancer dans le même conteneur. Modifier le nom de chaque image dans le docker-compose.yaml afin de les faires correspondre a vos nom d'image et version.
`$ docker-compose up`



## Tester 

- Pour tester l'application

`$ gradle test`

- Pour générer un rapport Jacoco

`$ gradle jacocoTestReport`

- Pour générer un rapport Jacoco de Couverture

`$ gradle jacocoTestCoverageVerification`
