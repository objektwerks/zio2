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
10. quill
11. ref/fiber ref
12. resources
13. streams
14. schedule
15. test

Build
-----
1. sbt clean compile

Test
----
1. sbt clean test

Run
---
1. sbt run
Multiple main classes detected. Select one to run:
1. objektwerks.ClockApp
2. objektwerks.CombinerApp
3. objektwerks.ConsoleApp
4. objektwerks.HttpServer
5. objektwerks.InterruptApp
6. objektwerks.LoggerApp
7. objektwerks.ParApp
8. objektwerks.QuillApp
9. objektwerks.RandomApp
10. objektwerks.STMApp
11. objektwerks.ScheduleApp
12. objektwerks.SemaphoreApp
13. objektwerks.ServicePatternApp
14. objektwerks.SubscriptionApp
15. objektwerks.SystemApp
16. objektwerks.ValidateApp

Curl
----
>Use the following curl sequences to target HttpServer:
1. curl -v http://localhost:7272/now
2. curl -d "Fred Flintstone" -v http://localhost:7272/greeting
3. curl --header "Content-Type: application/json" --request POST --data '{"Add":{"x":1,"y":1}}' http://localhost:7272/command
4. curl --header "Content-Type: application/json" --request POST --data '{"Multiply":{"x":1,"y":2}}' http://localhost:7272/command
5. curl --header "Content-Type: application/json" --request POST --data '{"Fake":{"x":1,"y":2}}' http://localhost:7272/command