FROM tomcat:10.1-jdk17-openjdk

# Удаляем дефолтные приложения Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Копируем наше приложение
COPY target/ROOT.war /usr/local/tomcat/webapps/ROOT.war

# Открываем порт
EXPOSE 8080

# Запускаем Tomcat
CMD ["catalina.sh", "run"]