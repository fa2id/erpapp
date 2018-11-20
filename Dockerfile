FROM openjdk:8

ADD target/erpapp-1.0.jar erpapp.jar

ENTRYPOINT ["java","-jar","erpapp.jar"]

EXPOSE 5000