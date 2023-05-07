FROM jenkins/slave:latest-jdk11

ENV TZ=Europe/Moscow
ENV DEBIAN_FRONTEND=noninteractive
USER root
RUN rm -rf /etc/apt/sources.list.d/stretch-backports.list
RUN apt update -y && apt install -y firefox-esr fontconfig && apt clean
