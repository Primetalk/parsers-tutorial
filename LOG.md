# Sequence of changes to the project in a reverse chronological order

### 1. Setup project

We'll use Maven for building our project. To get started we'll generate a sample project using maven
archetype plugin:

    mvn archetype:generate

It'll show plenty of available project archetypes. We can filter out only Scala projects.
The archetype we want is net.alchim31.maven:scala-archetype-simple. There are a few versions
and we select the latest (1.7).
`groupId` is "org.example"
`artifactId` is "parsers-tutorial"
`version` is default, 1.0-SNAPSHOT

After generation of the project we can check if it compiles and passes tests.

    mvn install

This ends the setup of a Maven-Scala project.
