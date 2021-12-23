FROM liquibase/liquibase
ADD /src/main/resources/db/changelog/ /liquibase/changelog
USER root
RUN cd changelog && mkdir -p db/changelog && mv v1 db/changelog
CMD ["sh", "-c", "docker-entrypoint.sh --url='jdbc:postgresql://172.23.0.1:5432/petdb?currentSchema=public' --username=doggo --password=roufrouf --classpath=/liquibase/changelog --changeLogFile=db-changelog.yaml update --log-level=info"]
