ZIO 2
-----
>ZIO 2 feature tests.

Build
-----
1. sbt clean compile

Test
----
1. sbt clean test

Run
---
1. sbt clean run
>Multiple main classes detected. Select one to run:
1. objektwerks.ConsoleApp
2. objektwerks.HttpServer

Curl
----
>Use the following curl sequences to target HttpServer:
1. curl -v http://localhost:7272/now
2. curl -d "Fred Flintstone" -v http://localhost:7272/greeting
3. curl --header "Content-Type: application/json" --request POST --data '{"Add":{"x":1,"y":1}}' http://localhost:7272/command
4. curl --header "Content-Type: application/json" --request POST --data '{"Multiple":{"x":1,"y":2}}' http://localhost:7272/command