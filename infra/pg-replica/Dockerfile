FROM postgres:11
RUN apt-get update && apt-get --assume-yes install iputils-ping && apt-get install --assume-yes ssh && apt-get install --assume-yes gosu
COPY ./setup-replica.sh /setup-replica.sh
RUN chmod +x /setup-replica.sh
ENTRYPOINT ["/setup-replica.sh"]
CMD ["gosu","postgres","postgres"]
