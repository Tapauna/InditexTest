
# Prueba técnica para Inditex

Prueba para Inditex que consiste en la implementacion de un servicio Rest para recuperar los productos similares dado un id de producto. 

Hecho con java 17, Maven 3.9.9 y Spring Boot 3.5.6

Para definir la Api he usado OpenApi Generator y para la implementacion de mapas MapStructs.
Además de la propia aplicación, se añade a la arquitectura un nodo de Redis para cachear las llamadas al servicio externo, al igual que un proxy nginx para balancear la carga entre tres replicas de la aplicación.

# Pasos para ejecutarlo

- Primero es necesario crear el contenedor Docker que contendrá tanto la simulación del servicio externo como los tests. Para ello es necesario ejecutar docker compose en el proyecto de las pruebas https://github.com/dalogax/backendDevTest  
  ```docker-compose up -d simulado influxdb grafana```
- Una vez creado el primer contenedor, debemos de ejecutar docker compose en nuestro proyecto. El contenedor contendrá las 3 replicas de la aplicación, el proxy para balancear la carga y Redis para cachear las llamadas al servicio.  
```docker-compose up```
- Una vez levantado el servicio, podremos ejecutar el banco de pruebas implementado en el proyecto de las pruebas  
```docker-compose run --rm k6 run scripts/test.js```
