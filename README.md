# tedtalks

This project was created using the [Ktor Project Generator](https://start.ktor.io).

Here are some useful links to get you started:

- [Ktor Documentation](https://ktor.io/docs/home.html)
- [Ktor GitHub page](https://github.com/ktorio/ktor)
- The [Ktor Slack chat](https://app.slack.com/client/T09229ZC6/C0A974TJ9). You'll need to [request an invite](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up) to join.


## Building & Running

To build or run the project, use one of the following tasks:

| Task                          | Description                                                          |
| -------------------------------|---------------------------------------------------------------------- |
| `./gradlew test`              | Run the tests                                                        |
| `./gradlew build`             | Build everything                                                     |
| `run`                         | Run the server                                                       | 

If the server starts successfully, you'll see the following output:

```
2024-12-04 14:32:45.584 [main] INFO  Application - Application started in 0.303 seconds.
2024-12-04 14:32:45.682 [main] INFO  Application - Responding at http://0.0.0.0:8080
```

## API specification
After running the project, visit http://0.0.0.0:8080/openapi for specifications.

## Decision-making process
### Influence calculation

The influence of each talk has been calculated via the formula: Views * Likes * 0.01. As how the influence calculation 
should work was not mentioned.

### Project structure
Three layered: Presentation, Domain, Data

In data layer, repository pattern have been used.

Routing is part of presentation,
Each service is part of Domain.
Repository and data sources are part of Data. 

### Technologies used
Ktor, Koin, Kotlin, MockK, Swagger


