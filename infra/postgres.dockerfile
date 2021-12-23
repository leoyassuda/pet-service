FROM postgres

RUN apk add --no-cache --virtual .build-deps g++ python3-dev libffi-dev openssl-dev && \
    apk add --no-cache --update python3 && \
    pip3 install --upgrade pip setuptools
RUN pip3 install wheel

COPY ./requirements.txt /requirements.txt
RUN pip3 install -r /requirements.txt

COPY ./patroni.yml /patroni.yml
ENTRYPOINT ["patroni", "patroni.yml"]