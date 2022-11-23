ZIO 2
-----
>Features tested:
1. config
2. fiber
3. http
4. json
5. queue
6. interrupt
7. zlayer
8. logging
9. promise
10. ref / fiber ref
11. resources
12. streams
13. schedule
14. test

Build
-----
1. sbt clean compile

Test
----
1. sbt clean test

Run
---
1. sbt clean run
Multiple main classes detected. Select one to run:
1. objektwerks.CombinerApp
2. objektwerks.ConsoleApp
3. objektwerks.HttpServer
4. objektwerks.InterruptApp
5. objektwerks.LoggerApp
6. objektwerks.ParApp
7. objektwerks.STMApp
8. objektwerks.ScheduleApp
9. objektwerks.SemaphoreApp
10. objektwerks.SubscriptionApp

Curl
----
>Use the following curl sequences to target HttpServer:
1. curl -v http://localhost:7272/now
2. curl -d "Fred Flintstone" -v http://localhost:7272/greeting
3. curl --header "Content-Type: application/json" --request POST --data '{"Add":{"x":1,"y":1}}' http://localhost:7272/command
4. curl --header "Content-Type: application/json" --request POST --data '{"Multiply":{"x":1,"y":2}}' http://localhost:7272/command
5. curl --header "Content-Type: application/json" --request POST --data '{"Fake":{"x":1,"y":2}}' http://localhost:7272/command