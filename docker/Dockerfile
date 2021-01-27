#基础镜像也可以用 java:8
#FROM openjdk:8-jdk-alpine

FROM java:8
MAINTAINER tonyZheng
VOLUME /tmp
ARG version=1.0.0
ADD gorgeous-doc.jar app.jar
RUN echo "Asia/shanghai" > /etc/timezone
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 5160