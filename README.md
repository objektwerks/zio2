ZIO 2
-----
>Features tested:
1. cache
2. config
3. direct
4. fiber
5. http
6. json
7. queue
8. interrupt
9. zlayer
10. logging
11. promise
12. quill
13. ref/fiber ref
14. resources
15. scalafx
16. streams
17. schedule
18. test

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
9. objektwerks.QuillH2App
10. objektwerks.QuillPostgreSqlApp
11. objektwerks.RandomApp
12. objektwerks.STMApp
13. objektwerks.ScalaFxApp
14. objektwerks.ScheduleApp
15. objektwerks.SemaphoreApp
16. objektwerks.ServicePatternApp
17. objektwerks.SubscriptionApp
18. objektwerks.SystemApp
19. objektwerks.ValidateApp
20. objektwerks.ZIODirect

Curl
----
>Use the following curl sequences to target HttpServer:
1. curl -v http://localhost:7272/now
2. curl -d "Fred Flintstone" -v http://localhost:7272/greeting
3. curl --header "Content-Type: application/json" --request POST --data '{"Add":{"x":1,"y":1}}' http://localhost:7272/command
4. curl --header "Content-Type: application/json" --request POST --data '{"Multiply":{"x":1,"y":2}}' http://localhost:7272/command
5. curl --header "Content-Type: application/json" --request POST --data '{"Fake":{"x":1,"y":2}}' http://localhost:7272/command

Postgresql Database
-------------------
>Example database url: postgresql://localhost:5432/todo?user=tripletail&password=
1. psql postgres
2. CREATE DATABASE todo OWNER your computer name.;
3. GRANT ALL PRIVILEGES ON DATABASE todo TO your computer name.;
4. \l
5. \q
6. psql todo
7. \i pg-ddl.sql
8. \q

Postgresql DDL
--------------
1. psql todo
2. \i pg-ddl.sql
3. \q