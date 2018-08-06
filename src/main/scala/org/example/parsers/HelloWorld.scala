package org.example.parsers

object HelloWorld {

  import fastparse.all._

  /** A parser that parses string "hello".
    *
    * `P` - is a helper function that gives the inner parser a name that is equal to the variable name.
    *       it is convenient for diagnostics.
    */
  val hello: Parser[Unit] = P("hello")

  /** Alternative parser. It'll try to parse "world" and if something goes wrong it backtracks
    * and tries to parse "World".
    */
  val world: Parser[Unit] = P("world" | "World")

  /**
    * Sequence parser. Parses the first word "hello"
    * then one or more spaces and then finally the word "world".
    *
    * None of the parsers capture any information, so the result of parser is Unit.
    * */
  val helloWorld: Parser[Unit] = P(hello ~ " ".rep(1) ~ world)

  /** Here we construct user-defined structure after successfully parsing
    * the text structure.
    */
  val helloWorldWithResult: Parser[String] = helloWorld.map(_ => "Hello, World!")

}
