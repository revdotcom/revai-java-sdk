FROM ubuntu:18.04

LABEL description="Image for running integration tests"

ENV MAVEN_OPTS="-Xmx512m -Djava.awt.headless=true"

RUN \
    apt-get -q update && \
    apt-get -q install bash && \
    apt-get install -y maven && \
    apt-get -q install -y git && \
    apt-get -q install -y gnupg && \
    apt-get -q install procps

# Install Java
RUN \
    apt-get -q install openjdk-11-jdk-headless

# Remove java certs and update them to allow maven dependency download
RUN \
    rm /etc/ssl/certs/java/cacerts && \
    update-ca-certificates --fresh

# Move the Maven settings file to the home directory
RUN mkdir -p /root/.m2
# COPY assets/settings.xml /root/.m2
COPY assets/settings.xml /etc/maven