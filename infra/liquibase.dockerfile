FROM liquibase/liquibase
ADD /src/main/resources/db/changelog/ /liquibase/changelog
USER root
RUN cd changelog && mkdir -p db/changelog && mv v1 db/changelog
COPY infra/liquibase.properties /liquibase/changelog
CMD ["sh", "-c", "docker-entrypoint.sh --defaultsFile=/liquibase/changelog/liquibase.properties --classpath=/liquibase/changelog --changeLogFile=db-changelog.yaml update --log-level=info"]
