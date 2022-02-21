FROM ahanaio/prestodb-sandbox

#RUN touch /etc/catalog/mongodb.properties
#RUN touch /etc/catalog/postgresql.properties

COPY ./postgresql.properties /opt/presto-server/etc/catalog
COPY ./mongodb.properties /opt/presto-server/etc/catalog
