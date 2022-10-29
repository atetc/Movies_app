# Movies app

### Description:

This project contains best practices of displaying list of movies with details

Project targeted for open API [OMDB](http://www.omdbapi.com), but you need to generate your own token and set is as parameter into `gradle.properties`
as `omdbToken=<TOKEN>` or use `omdbToken` ENV

Used in project:
* Kotlin
* Jetpack Compose + MVVM
* Coroutines
* Dependency Injection based on Dagger + Hilt
* Network communication based on Retrofit + Calls interception
* Sample of external API library
* Integration test for API library
* Unit test for domain layer