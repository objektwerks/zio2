ZIO 2
-----
>Features tested:
1. config
2. fiber
3. fiber ref
4. http
5. json
6. queue
7. ref
8. resources
9. streams
10. test

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
1. objektwerks.CombinerApp
2. objektwerks.ConsoleApp
3. objektwerks.HttpServer
4. objektwerks.LoggerApp

Curl
----
>Use the following curl sequences to target HttpServer:
1. curl -v http://localhost:7272/now
2. curl -d "Fred Flintstone" -v http://localhost:7272/greeting
3. curl --header "Content-Type: application/json" --request POST --data '{"Add":{"x":1,"y":1}}' http://localhost:7272/command
4. curl --header "Content-Type: application/json" --request POST --data '{"Multiply":{"x":1,"y":2}}' http://localhost:7272/command
5. curl --header "Content-Type: application/json" --request POST --data '{"Fake":{"x":1,"y":2}}' http://localhost:7272/command