Steps to use database connection in the project:

1. In application.yml, declare *datasource*. It can be done like this:
```
spring
  datasource:
    driverClassName: oracle.jdbc.driver.OracleDriver
    url: jdbc:oracle:thin:@//address:port/sid
    username: ...
    password: ...
```

2. In build.gradle, declare dependency: `compile("com.oracle:ojdbc6:version")`

3. Due to Oracle license restriction, no public Maven repository provides ojdbc driver. You must host a maven repository on your local server or add ojdbc driver to your local Maven repository.
   To get the ojdb driver, download ojdbc6.jar from [oracle website](http://www.oracle.com/technetwork/database/features/jdbc/index-091264.html). To add it to your local Maven repo, use the following command:
```
mvn install:install-file -Dfile={Path/to/your/ojdbc.jar} -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0 -Dpackaging=jar
```

4. To use the local Maven repo, add following line to the repositories block of your build.gradle: 
```
repositories {
  mavenLocal()	
}
```

