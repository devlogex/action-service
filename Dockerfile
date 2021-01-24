FROM pw_env
WORKDIR /data/actionservice
COPY . .
RUN mvn -B -f /data/actionservice/pom.xml -s /usr/share/maven/ref/settings.xml clean package
CMD ["java", "-jar", "runner/target/action-runner-jar-with-dependencies.jar"]