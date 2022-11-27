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
1. objektwerks.ClockApp
2. objektwerks.CombinerApp
3. objektwerks.ConsoleApp
4. objektwerks.HttpServer
5. objektwerks.InterruptApp
6. objektwerks.LoggerApp
7. objektwerks.ParApp
8. objektwerks.RandomApp
9. objektwerks.STMApp
10. objektwerks.ScheduleApp
11. objektwerks.SemaphoreApp
12. objektwerks.ServicePatternApp
13. objektwerks.SubscriptionApp
14. objektwerks.SystemApp
15. objektwerks.ValidateApp

Curl
----
>Use the following curl sequences to target HttpServer:
1. curl -v http://localhost:7272/now
2. curl -d "Fred Flintstone" -v http://localhost:7272/greeting
3. curl --header "Content-Type: application/json" --request POST --data '{"Add":{"x":1,"y":1}}' http://localhost:7272/command
4. curl --header "Content-Type: application/json" --request POST --data '{"Multiply":{"x":1,"y":2}}' http://localhost:7272/command
5. curl --header "Content-Type: application/json" --request POST --data '{"Fake":{"x":1,"y":2}}' http://localhost:7272/command