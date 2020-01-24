# Mutantes API - Challenge MeLi

El desafio consiste en desarrollar una API REST para detectar si un ADN humano es mutante.

## Tecnologias

- [Spring Boot](https://spring.io/projects/spring-boot)
- [MongoDB](https://www.mongodb.com/es)
- [Maven](https://maven.apache.org/)
- [Google Cloud](https://cloud.google.com/)
- [JUnit 5](https://junit.org/junit5/)
- [Mockito 3](https://site.mockito.org/)

## Instalacion

Es necesario tener instalado:

- [Java 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven](https://maven.apache.org/)
- [MongoDB](https://www.mongodb.com/es)
- [Git](https://git-scm.com/)

```
$ git clone https://github.com/leonelavalos/challenge.git
$ cd challenge
$ mvn clean package
$ mvn spring-boot:run
```
De esta manera, la aplicacion se encontraria levantada en 'http://localhost:8080/' y habria que utilizar esta URL si quisieramos probar localmente las APIs a traves de [Postman](https://www.getpostman.com/) por ejemplo.

## Tests

Para correr los test, nos posicionamos en el directorio del proyecto y corremos el siguiente comando:

```
$ mvn verify
```
Ademas, al correr dicho comando, se genera un reporte del coverage tanto de los tests unitarios como el de integracion.
El mismo se aloja en el siguiente directorio:

```
$ cd target/site/jacoco-merged-test-coverage-report/index.html
```

## Deploy de la API

Al no haber utilizado nunca la plataforma de Google Cloud, me decidi por ella.
Cree una instancia de una maquina virtual, permitiendo el trafico http.

A diferencia del ambiente de desarrollo, donde la base de datos de MongoDB se almacenaba localmente, configure una base de datos en la nube con [MongoDB Atlas](https://www.mongodb.com/cloud/atlas), consiguiendo un Cluster del tipo replica divido en tres nodos.

Por lo que hubo que hacer una conexion peering entre el Cluster y Google Cloud para poder permitir el acceso remoto.

HOST: https://mutantes-app.appspot.com


## Usos

- Request - ADN Mutante:

```bash
POST → https://mutantes-app.appspot.com/api/mutant
{
   "dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}  
```

- Response:

```
200 OK
```

- Request - ADN Humano:

```bash
POST → https://mutantes-app.appspot.com/api/mutant
{
   "dna":["ATGCCA","CTGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
} 
```
- Response:

```
403 Forbidden
```

- Request - Estadisticas de las verificaciones de ADN:

```bash
GET → https://mutantes-app.appspot.com/api/stats
```

- Response:

```
{
    "countMutantDna": 1,
    "countHumanDna": 1,
    "ratio": 1.0
}
```