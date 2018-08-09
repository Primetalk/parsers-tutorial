# Sequence of changes to the project in a reverse chronological order

### 5. Construct new parsers from smaller ones

The list of basic combinators are in `ParserCombinatorsOverview.scala`.
Using these building blocks we may create more
complex parsers from smaller.

### 4. Parse arithmetic expressions

Parsers produce some value. Usually for representing complex values an algebraic data type is used.
In parser's terms it's often called AST - abstract syntax tree. It represents the model of
what we parse and what is encoded in the parsed string.

Let's model arithmetic expressions. (See `arithmeticExpression.scala`.) In our simple model
an arithmetic expression is either a number, or an operation over two other expressions.
(We don't support unary +/- for simplicity.)

Let's start with a parser that can parse a number.

    CharIn('0'to'9').rep(1).!
    
This reads as any digit repeated at least once. Exclamation point means that we
want to preserve the value for further processing. This further processing 
typically involves some conversion to user-defined data structure. For instance,
AST:

    .map(_.toInt).map(Number)

The next thing is the ability to parse expressions in parentheses:

    "(" ~/ addSub ~ ")"
    
Here `addSub` is another parser (yet to be defined) that will parse 
sums and subtractions. We can directly sum expressions that might contain 
multiplication or division:

    val addSub: P[Int] = P( divMul ~ (CharIn("+-").! ~/ divMul).rep ).map(eval)
    
Similarly, we can directly mupltiply factors that are either numbers or 
expressions in parentheses:

    val factor: P[ArithmeticExpression] = P( number | parens )
    val divMul: P[ArithmeticExpression] = P( factor ~ (CharIn("*/").! ~/ factor).rep ).map(eval)
   
And finally, in order to enforce parser to parse the whole string, and not just 
laziliy parse the first small expression, we request that the parser succeeds
only when end-of-line is encountered:

    val expr  : P[ArithmeticExpression] = P( addSub ~ End )
    
That's a complete parser. (In `arithmetic.scala` a function `eval` is
provided to evaluate the constructed/parsed `ArithmeticExpression`.) 
    
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
