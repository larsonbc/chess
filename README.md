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
## Server Design Sequence Diagram

https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdOUyABRAAyXLg9RgdOAoxgADNvMMhR1MIziSyTqDcSpymgfAgEDiRCo2XLmaSYCBIXIUNTKLSOndZi83hxZj9tgztPL1GzjOUAJIAOW54UFwtG1v0ryW9s22xg3vqNWltDBOtOepJqnKRpQJoUPjAqQtQxFKCdRP1rNO7sjPq5ftjt3zs2AWdS9QgAGt0OXozB69nZc7i4rCvGUOUu42W+gtVQ8blToCLmUIlCkVBIqp1VhZ8D+0VqJcYNdLfnJuVVk8R03W2gj1N9hPKFvsuZygAmJxObr7vOjK8nqZnseXmBjxvdAOFMLxfH8AJoHYckYB5CBoiSAI0gyLJkHMNkjl3ao6iaVoDHUBI0HfAYDy-T4nhtJZdj6A4sJBQoN13D8a3In9nmDW0aIBc5NyVbUhxgBAEKQNBYXgxDUXRWJsUHXVe2TMkKTNOEAwLHsi2TN0OXIXl+X9MjDAlCApRuDSmS06cB2VQSVMZCcpwJBSWVTY1MkzbNc1Y9SkwVUsdO9X0DM-FA6wbc82yjGMR3Ml0S34ycVU7cL-wchMZ143cJNElc10wJiQQS5jSJC79Hl-FKLzK2jb0KwoHwwZ9XxItTqtPSr0Gqg4QLA7w-ECLwUDbCTfGYZD0kyTAGuYIr5wqaQ9PqLlmhaAjVCI7o-wvWq2QK4cOrQfLMoY7dEsE4T7FG8SENGqSMVkmz5M0lyYHJMAPJzLb0ELCy-MKMtuT5AVkuzCKkmMqUYrkxNnINDgUG4dyG1hL60B+uLtPKBa+SWkHRwvcVJTx2K+ys07wTSwwyb2o6gTvMn6NKHayempq316brOF6iDAkhDg4OhGAAHF81ZcbUKm9C8msnc5qFrk8Jaex802g7mcY479tB1KCswmzymQWIRdGVRxOhY21DumTKae36DTej6UYO9HSf+nTAf0vGwcJkziehpznoNQ2wAt02Xcst3OT04GACofalZXRhJyyEvBV7RZ5aEbepzWBaN0XcoQddjq3Rm9z6RO1HGSp+krj1pGrgBGJ8AGYABYnhQzIVLuFYvh0BBQGbHvDwmL5K69fMx72GBGjo4p6dOVmYBfdna9F6uKnX0Z66b1uO6mLvTUMvungHoeR7Y8f80n0Zp9nznQM8PrIOwHwoGwbh4DcwwLZSCa0I5BmjLEo5QcINBWpXVW2sLzvgnvmeestdq51Ru+KY8Cr4wB4nTE6g5XLpkyBbWEcAf4WytlibOAc7YplehSR2qNw5-XZFHIGgpUbxz9o9GGgcaFvSIXXaQjDXT+RYZ7ARHDK7Jz8qnJKaoNSUJkLDGhaYTT8JvvmIRJZI7liCunUYt9Qp6JQPXdsMZJG+WETIwSAjs4ZRweUEhBCUBkLUHlXWDMF7zm3sYhu5Rm7t0QccFmUswBsxIgIveASzBc2fjzAIlgEbCWSDAAAUhAUSwt8yBHPiAZsksgF61lmAqolJFZQOCAdd8n9gAJKgHACAwkoCzAEYE+mGt7Fe3-Gg54g9an1MaSsAA6iwD0S0WgACEeQKDgAAaWvjvXxMB-FtywbTOchSzrlAAFbpLQEQtJokXFonugoixvC6HIwYVI4R2iPbA3YRDThAlbZxSUiHfMsIBGaMxrpVhRiTGPMkf7RRPDyh+C0IQj51S+kNOgM0-M9dvkiJgJSKo0gFC40rrpQUVRemUH6dAGAABeGA0L8WwtgMMysXJMkLOJf86Q1z4ogIptDOxc4mbuNmkzei94QlhI5tEp+4F+oBC8DU+A3A8CdmwJ-Qg8REj-wltNDZ2FsYK2Wq0Yw6szj2LWXxFlSUQBSrhA9Z53DqGphNSpWESLtHqtxo8sywKzlWvTFAR2drmEyEWjSx5UMuFUNeYaE1RCvVlgdX6om5ilGl31rY9pHL9WLxAZcZmS9+Ur2aoKoVmAgA
