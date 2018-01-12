##Very simple application with Spring reactive web and mongo


Run Mongo : 

`` docker-compose up `` 

Load some data :

`` curl -XPOST http://localhost:8080/account/load?size=10000 ``

Read data in reactive way :

``curl -H "Accept: text/event-stream" "http://localhost:8080/account/reactive/"``



Server events :

``https://www.html5rocks.com/en/tutorials/eventsource/basics/``