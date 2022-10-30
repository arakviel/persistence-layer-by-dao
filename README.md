# Native Persistence Layer by Data Mapper pattern
**Persistence** - `C`reate/`R`ead/`U`pdate/`D`elete records to disk or database. \
**Layer** - insulation. \
**Persistence Layer** (aka **Data Access Layer**) = isolate `C`reate/`R`ead/`U`pdate/`D`elete logic from the business logic and presentation layer.

_Use project for study_

## Technologies
Project is created with:
* Java version: 18;
* Gradle version: 7.4;
* H2 database version: 2.1.214;
* Used IntelliJ IDEA version: 2022.2.1 (Ultimate Edition).

## Dependencies
Dependencies managed with gradle and [mvnrepository.com](https://mvnrepository.com/).
* Java Development Kit (JDK) 18+;
* Spotless ([github](https://github.com/diffplug/spotless), [mvn](https://mvnrepository.com/artifact/com.diffplug.spotless/spotless-plugin-gradle)) for static analyzer and code style linter;
* H2 database driver ([mvn](https://mvnrepository.com/artifact/com.h2database/h2));
* Google Java Code Style (included in spotless [read more](https://google.github.io/styleguide/javaguide.html))

## Setup
#### Windows:
TODO:\
#### Linux:
TODO:\
#### Mac:
TODO:\

recommend to used [sdkman.io](https://sdkman.io/) to install java and gradle.

## Code examples
TODO:\

## Used features

### OOP and Java
* Encapsulation;
* Inheritance;
* Polymorphism;
* Abstraction;
* Static methods and fields;
* Interfaces;
* Abstract classes;
* Generics;
* Stateless classes;
* Immutable classes (Records);
* Custom Exceptions;
* Utilities classes;
* Helper classes;
* POJO aka Java Beans classes;
* Nested Static classes;
* Anonymous classes;
* Lambda;
* Functional Interfaces;
* Stream API;
* Java Collections API;
* Date Time API;

### Used principles
* `K`eep `I`t `S`tupid `S`imple;
* `D`o not `R`epeat `Y`ourself;
* `Y`ou `A`re not `G`onna `N`eed `I`t;
* `S`ingle `L`evel of `A`bstraction `P`rinciple;
* `S`ingle Responsibility Principle;
* `O`pen Closed Principle;
* `L`iskov's Substitution Principle;
* `I`nterface Segregation Principle;
* `D`ependency Inversion Principle;
* Code for Interface not for Implementation.

### Used GRASP principles / patterns
* High Cohesion;
* Low Coupling;
* Polymorphism;
* Information Expert;
* Creator;
* Pure Fabrication;
* Indirection.

### Used GoF patterns
* Singleton;
* Builder;
* Strategy;
* Proxy;

### Other patterns
* Lazy Initialization via Virtual proxy;
* Data Mapper.

