ZIO 2
-----
>Features tested:
1. cache
2. config
3. fiber
4. http
5. json
6. queue
7. interrupt
8. zlayer
9. logging
10. promise
11. quill
12. ref/fiber ref
13. resources
14. streams
15. schedule
16. test

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
1. objektwerks.CacheApp
2. objektwerks.ClockApp
3. objektwerks.CombinerApp
4. objektwerks.ConsoleApp
5. objektwerks.HttpServer
6. objektwerks.InterruptApp
7. objektwerks.LoggerApp
8. objektwerks.ParApp
9. objektwerks.QuillApp
10. objektwerks.RandomApp
11. objektwerks.STMApp
12. objektwerks.ScheduleApp
13. objektwerks.SemaphoreApp
14. objektwerks.ServicePatternApp
15. objektwerks.SubscriptionApp
16. objektwerks.SystemApp
17. objektwerks.ValidateApp

Curl
----
>Use the following curl sequences to target HttpServer:
1. curl -v http://localhost:7272/now
2. curl -d "Fred Flintstone" -v http://localhost:7272/greeting
3. curl --header "Content-Type: application/json" --request POST --data '{"Add":{"x":1,"y":1}}' http://localhost:7272/command
4. curl --header "Content-Type: application/json" --request POST --data '{"Multiply":{"x":1,"y":2}}' http://localhost:7272/command
5. curl --header "Content-Type: application/json" --request POST --data '{"Fake":{"x":1,"y":2}}' http://localhost:7272/command