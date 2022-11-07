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
2. objektwerks.HttpApp ( curl -v http://localhost:7272/now | curl -H "Content-Type: text/plain" -d "Fred Flintstone" -v http://localhost:7272/greeting )