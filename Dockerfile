FROM gradle:8.7-jdk21

WORKDIR /app

COPY app .

RUN ./gradlew clean installDist

RUN chmod +x build/install/app/bin/app

CMD ["./build/install/app/bin/app"]
