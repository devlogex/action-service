FROM maven AS build
COPY settings.xml /usr/share/maven/ref/settings.xml
COPY common /home/root/action-service/common
COPY runner /home/root/action-service/runner
COPY sdk /home/root/action-service/sdk
COPY dbservice /home/root/action-service/dbservice
COPY comments /home/root/action-service/comments
COPY todos /home/root/action-service/todos
COPY pom.xml /home/root/action-service/pom.xml
RUN mvn -B -f /home/root/action-service/pom.xml -s /usr/share/maven/ref/settings.xml clean package

FROM openjdk:8
COPY --from=build /home/root/action-service/runner/target/action-runner-jar-with-dependencies.jar /var/lib/action-runner-jar-with-dependencies.jar
EXPOSE 9004
EXPOSE 8004
CMD ["java","-jar","/var/lib/action-runner-jar-with-dependencies.jar"]