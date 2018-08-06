# Sequence of changes to the project in a reverse chronological order


### 3. Introduce Parsers

Parsers are functions that convert strings to some meaningful data. For example, we could use `Int.parse`
to convert a `String` to `Int`. Or `DateTime.parse` to get `DateTime`.

When we have a simple parser, it consumes the whole string and we cannot combine a few parsers to produce
another more powerful parser. In parser combinators libraries, individual parsers consumes only some part of string
and return the rest of the string for further processing:

    type Parser[A] = String => (A, String)

For example, `Parser[Int]` will scan the given string until there are digits and convert all found digits
to an integer. It'll stop conversion when a non digit is encountered. We can pass the rest of the input string
to another parser.

When we try to parse a string that has something different form what we expect, the parser will return failure
instead of the result. So, `Parser` type is slightly different -

    type Parser[A] = String => Failure | Success (A, String)

Let's see a couple of examples (`HelloWorld.scala` and `HelloWorldTest.scala`).

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
