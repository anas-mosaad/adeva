# Running the project
First, make sure you have java JDK installed. Java version 8 or higher.
You can download from https://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html

For minimal setup and configuration, I use H2 database.
If you want to you a database of your own, you can configure in `application.properties` file.

 
To run the application, go to the command line and run.
```bash
./gradlew bootRun
```

You can run the test cases by running
```bash
./gradlew test
```

The application is being built as one fat jar. You can build and package the application by running.
```bash
./gradlew assemble
```

 