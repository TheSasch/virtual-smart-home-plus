FROM patriotframework/simulator-base:1.8

COPY target/virtual-smart-home-plus-1.0-SNAPSHOT-jar-with-dependencies.jar ./app.jar

CMD java -jar /app.jar
