## How to run the containers
While being in the root directory of the project execute the following commands:
```
docker build -t demo-mca:1.0 .
docker-compose up -d simulado influxdb grafana demo-mca
```
4 containers should be running after this. Now you can send a request to [http://localhost:5000/product/{productId}/similar](http://localhost:5000/product/{productId}/similar) replacing {productId} with the id of the desired product (e.g. http://localhost:5000/product/1/similar).
