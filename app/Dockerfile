FROM openjdk:19-jdk-alpine3.16

RUN mkdir /app

WORKDIR /app

RUN apk add --no-cache bash 

COPY . /app

CMD ["./gradlew", "run"]