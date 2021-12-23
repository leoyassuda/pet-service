FROM postgres:11
COPY ./setup-main.sh /docker-entrypoint-initdb.d/setup-main.sh
RUN chmod 0666 /docker-entrypoint-initdb.d/setup-main.sh
RUN apt-get update && apt-get --assume-yes install iputils-ping && apt-get install --assume-yes ssh
