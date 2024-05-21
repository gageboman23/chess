# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
## Sequence Diagram

https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdOUyABRAAyXLg9RgdOAoxgADNvMMhR1MIziSyTqDcSpymgfAgEDiRCo2XLmaSYCBIXIUNTKLSOndZi83hxZj9tgztPL1GzjOUAJIAOW54UFwtG1v0ryW9s22xg3vqNWltDBOtOepJqnKRpQJoUPjAqQtQxFKCdRP1rNO7sjPq5ftjt3zs2AWdS9QgAGt0OXozB69nZc7i4rCvGUOUu42W+gtVQ8blToCLmUIlCkVBIqp1VhZ8D+0VqJcYNdLfnJuVVk8R03W2gj1N9hPKFvsuZygAmJxObr7vOjK8nqZnseXmBjxvdAOFMLxfH8AJoHYckYB5CBoiSAI0gyLJkHMNkjl3ao6iaVoDHUBI0HfAYDy-T4nhtJZdj6A4sJBQoN13D8a3In9nmDW0aIBc5NyVbUhxgBAEKQNBYXgxDUXRWJsUHXVe2TMkKTNOEAwLHsi2TN0OXIXl+X9MiUCDBYlhgAAxcIagAWWrGAAHUWErLlqzuGAAF4YAAfg0pktOnAdlUEm4YFhZIMlSGBUn0VIUA4GAqPeWTAswwLyioYBkC0TIqk4pZYQSu1qxcArsRgMrt0nBMCQUllU2NTJM2zXNWPU8qZBq11Sx071fQMz8jM7BtzzbKMYxHTA2qTBV-Iq8FBuzYa0Am8q5Jmpj5wk0SVzXZayvWrd6KuUj+u-R5fyG-9Tto3aKrvGaHwwZ9XxItSrtPC6Lyug4QLA7w-ECLwUDbCTfGYZD0kyTAHuYfiSnKCppD0+ouWaFoCNUIiSL-T7-lvBiziBZi+mx9BuMwfbYbm4T7FB8SENBqSMSSgT5M02qYBKJAxUsRrRwvWESbQQtfOmwoy25PkBXmvm2ws6zpfsxzwmcwX3K8nyXRLSmVQ5jKkA4FpRPSzKbtW6q2YNDgUG4BqPvQAW7aFm6ps6sWdMRvlkfMyybJHRWnOlxa1e8yaOq1gKBPKVdi1N5K1t43cQazbaECwNqKdhonYjkdoVgk8k4tBmBo+TMV1QQSxrra+j73Qx6YBfN9ej6bOwFziZ89imAi5Llky41Svvs4X6IMCSE4p5aEYAAcXzVlwdQqG65hiO4cqaeuTwlp7HzLHHbJmuZwT+d+kFsmKdXqnoVn0ZVHE6+58ZmSJync2RYNTnud5xaHYW-9hc1tpTkekpaqzlr7Bs-tlaB3-MHDWfYZqDjSnrA2RsUGxxZomMOSkwAKA1DfNQsJSrlRdiWN2wDJaCgAFTe3ljvW+zsw4HVSkJaENQxQEI0NXOOR9CYbQfrfFOadyoZ1XkTehahxisNiOwzhBxuE7hBKcaGT0m79AkaoKRyAZEcLnkPUCng-qQWwD4KA2BuDwHqoYAhKQIZoRyCvW684cINFRhIvef8cbsQkV6Q8uND6MWPkdM+FEpg+L8asHifCUqR0NFYghsI4DxPzE-LEL8qrtQtimDmlAuY80dr-GWTtSFAN0pQmBF5aEQOzFAlWjs4ElMQSw42+tDZoBaaBBRlVDBYKyXVdMmQEnhMDC5fMxCyqNPIeWXq7YYwaJCsMgaalMQwAAGoKB5JSLkZAQqeVmJ5cZmT37h1mjrCRHpOkrR4YEvh5QkkDJQAQoRN1RFOKOucj43j8y+LYjeLpd1lHL1USRD5NEniLLJj9Qxo8AiWGtsJZIMAABSEBRIz3zIEHQCBQDNiXg4mJa9qiUi3u45uISvmjAuWCqYIAIDCSgAfYod0blzmCfvUJfRQUctpfS8+x8CVzQAFaorQAklFoknkoDREzdJPS36azSrkr+BTBYAIQVMiW+kKmyx9grByAdVYeW8o07WgkOltI6Rg7prNjk4KGfmC5hzJnsgoVqmh4CYDzP1dAj5cDQ5ZOYbEiR0gKS-A0DdLpr8jkKs7BwDgVRsVwqgE2e1lLCpqVmDy6Ahz-XHNKZSKo0gFBe3mdyQUWbYAeTUrUz1Dq4pGpgAoL00hDR0ugJGHZXpKQ8h5BGkhTCmmxN7uoPtZVVq8NZcikVkrVyp1HQTOcga179Fbu3Po4q0BdwkcXHwxZ+4Vyrm1K5ija4OOBc3VdmiJgbq3Ri4dqh92DzMMPEe-0AheGAGERA6ZYDAGwOYwg8REi2MXtDAl2EPabxRq0YweM2TrSuFExdprUzcDwIyPQBhEloeXFK6SaSzbRuLKhn9Kk75qr8lMyDXsPU3A0Cay+Ot72yptTGkAOHOFEPgZRl1MgkbOQ9Ro7j00UM7pjoR0hJG8Df3-OR4TrtePUYE7q8aDHTmCWY+OllwI7k4eea8w6e4W7yDblevoGH9CGHYz+2K11D6ArPQ3Z6F6TNroswYQ0OHbMHGfaBIAA