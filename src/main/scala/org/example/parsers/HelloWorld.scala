/**
  * Copyright (C) 2015 Cotiviti Labs (nexgen.admin@cotiviti.io)
  *
  * The software code contained herein is the property of Cotiviti Corporation
  * and its subsidiaries and affiliates (collectively, “Cotiviti”).
  * Access to this software code is being provided to you in the course of your
  * employment or affiliation with Cotiviti and may be used solely in the scope
  * and course of your work for Cotiviti, and is for internal Cotiviti use only.
  * Any unauthorized use, disclosure, copying, distribution, destruction of this
  * software code, or the taking of any unauthorized action in reliance on this
  * software code, is strictly prohibited.
  * If this information is viewed in error, immediately discontinue use of the
  * application.  Anyone using this software code and the applications will be
  * subject to monitoring for improper use, system maintenance and security
  * purposes, and is advised that if such monitoring reveals possible criminal
  * activity or policy violation, Cotiviti personnel may provide the evidence of
  * such monitoring to law enforcement or other officials, and the user may be
  * subject to disciplinary action by Cotiviti, up to and including termination
  * of employment.
  *
  * Use of this software code and any applications and information therein
  * constitutes acknowledgement of and consent to this notice
  */
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
