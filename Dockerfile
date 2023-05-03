FROM ubuntu:20.04

ENV TZ=Europe/Moscow
ENV DEBIAN_FRONTEND=noninteractive
RUN apt update -y && apt install -y software-properties-common && apt clean
RUN add-apt-repository ppa:mozillateam/ppa
RUN apt update -y && apt install -y firefox && apt clean
