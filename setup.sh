#!/bin/bash

printf "Starting cluster\n"
docker-compose up -d
sleep 1

printf "Configuring mongo replica set\n"
docker-compose exec mongo1 mongo --eval 'rs.initiate({_id : "r0", members: [{ _id : 0, host : "mongo1:27017", priority : 1 },{ _id : 1, host :"mongo2:27017", priority : 0 },{ _id : 2, host : "mongo3:27017", priority : 0, arbiterOnly: true }]})'

printf "Waiting 30 seconds for connect to be ready...\n"
sleep 30

printf "Configuring photo-connector\n"
curl -X POST \
  http://localhost:8083/connectors \
  -H 'Accept: */*' \
  -H 'Accept-Encoding: gzip, deflate' \
  -H 'Connection: keep-alive' \
  -H 'Content-Length: 558' \
  -H 'Content-Type: application/json' \
  -H 'Host: localhost:8083' \
  -H 'User-Agent: PostmanRuntime/7.20.1' \
  -H 'cache-control: no-cache,no-cache' \
  -d '{
  "name": "photo-connector",
  "config": {
    "connector.class": "io.confluent.connect.elasticsearch.ElasticsearchSinkConnector",
    "tasks.max": "1",
    "topics": "photo",
    "key.converter": "org.apache.kafka.connect.storage.StringConverter",
    "value.converter": "org.apache.kafka.connect.json.JsonConverter",
    "value.converter.schemas.enable": "false",
    "schema.ignore": "true",
    "connection.url": "http://elastic:9200",
    "type.name": "kafka-connect",
    "behavior.on.malformed.documents": "warn",
    "name": "photo-connector"
  }
}'

printf "Configuring long-exposure-connector\n"
curl -X POST \
  http://localhost:8083/connectors \
  -H 'Accept: */*' \
  -H 'Accept-Encoding: gzip, deflate' \
  -H 'Connection: keep-alive' \
  -H 'Content-Length: 582' \
  -H 'Content-Type: application/json' \
  -H 'Host: localhost:8083' \
  -H 'User-Agent: PostmanRuntime/7.20.1' \
  -H 'cache-control: no-cache,no-cache' \
  -d '{
  "name": "long-exposure-connector",
  "config": {
    "connector.class": "io.confluent.connect.elasticsearch.ElasticsearchSinkConnector",
    "tasks.max": "1",
    "topics": "long-exposure",
    "key.converter": "org.apache.kafka.connect.storage.StringConverter",
    "value.converter": "org.apache.kafka.connect.json.JsonConverter",
    "value.converter.schemas.enable": "false",
    "schema.ignore": "true",
    "connection.url": "http://elastic:9200",
    "type.name": "kafka-connect",
    "behavior.on.malformed.documents": "warn",
    "name": "long-exposure-connector"
  }
}'

printf "Connecting to server\n"
docker-compose logs -f server