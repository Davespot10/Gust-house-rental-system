FROM adoptopenjdk/maven-openjdk11
WORKDIR /mpp
COPY . .
RUN mvn clean install
CMD mvn spring-boot:run
EXPOSE 8080
#EXPOSE 8081
#ADD /target/mppproject-0.0.1-SNAPSHOT.jar mppproject.jar
#ENTRYPOINT ["java", "-jar", "mppproject.jar"]
