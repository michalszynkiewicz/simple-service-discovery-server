# Simple Service Discovery server

A "service discovery" server used in the [Quarkus Insights $70](https://www.youtube.com/watch?v=l3mLKU3wR2A).

## Running
To start the service, build it and run the created jar:
```shell
./mvnw clean package
java -jar target/quarkus-app/quarkus-run.jar
```

This will expose the service on http://localhost:9090.

## Usage
To register a service, POST a JSON file describing it to http://localhost:9000/services:
```shell
curl -v -H "Content-Type: application/json" -d '        
{
  "serviceName": "configurable-service",
  "url": "http://127.0.0.1:8506",
  "labels": ["v2"]
}' http://localhost:9090/services
```

To list services of a given name, query http://localhost:9000/service-name, e.g.
```
curl localhost:9090/services/configurable-service
```

This, assuming you have registered two services with service name `configurable-service`, will return JSON similar to:
```json
[
  {
    "serviceName": "configurable-service",
    "labels": [
      "v1"
    ],
    "url": "http://127.0.0.1:8406"
  },
  {
    "serviceName": "configurable-service",
    "labels": [
      "v2"
    ],
    "url": "http://127.0.0.1:8506"
  }
]

```
