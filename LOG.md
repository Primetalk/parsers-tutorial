# Sequence of changes to the project in a reverse chronological order

### 2. Add library dependency

There are a lot of parser combinators libraries for Scala. We'll use FastParse (we could also use Parboiled2,
scala-parser-combinators, ...). Let's add the dependency to `pom.xml`. The dependency looks like

    "com.lihaoyi" %% "fastparse" % "1.0.0"

In maven format it's

    <dependency>
      <groupId>com.lihaoyi</groupId>
      <artifactId>fastparse_2.12</artifactId>
      <version>1.0.0</version>
    </dependency>

(We'll use environment variables.)

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
