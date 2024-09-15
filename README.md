# Blazemeter Karate Demo
Demo project for karate [test-doubles](https://github.com/karatelabs/karate/tree/master/karate-netty) running on [Blazemeter](https://www.blazemeter.com/) it is based on [Karate Gatling integration](https://github.com/karatelabs/karate/tree/master/karate-gatling)

## Instructions

# Running Locally

Build the gradle jar with:

```
./mvnw clean gatling:enterprisePackage
```

Run with taurus:

```
bzt karate-gatling.yml
```