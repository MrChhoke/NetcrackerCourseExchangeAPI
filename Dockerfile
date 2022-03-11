FROM openjdk:11
ADD target/ExchangeAPI.war ExchangeAPI.war
EXPOSE 8198
ENTRYPOINT ["java", "-jar", "ExchangeAPI.war"]