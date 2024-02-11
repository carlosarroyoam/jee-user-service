FROM airhacks/glassfish
COPY ./target/jee-user-service.war ${DEPLOYMENT_DIR}
