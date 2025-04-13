# Spring Boot : Personal Docs

Before running the spring boot in your local machine, you should be installing tools or library:  

1. **`Maven`** version up 3.5 or later  
2. **`Java`** version 17.0 or later

For reference, I'm using  [sdkman](https://sdkman.io/) for installing the tools or library on above. installing sdkman using terminal `bash`

## Running on local machine

1. `mvn spring-boot:run` : to running your spring-boot in local machine
2. `mvn clean install` : to clean install your package  
3. `mvn dependency:purge-local-repository` :  to purge the dependency in your local machine
