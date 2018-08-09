package org.example.parsers

import fastparse.all._

trait ParserCombinatorsOverview[T, A<:T, B<:T] {
  /** If we have a couple of simple parsers: */
  val a: P[A]

  val b: P[B]

  // then we can compose the following bigger parsers:
  val p0 : P[Unit]      = "hello"   // exact string

  val p1 : P[(A,B)]     = a ~ b     // sequence

  val p2 : P[T]         = a | b     // alternative

  val p3 : P[(A,B)]     = a ~/ b    // prevents backtracking

  val p4 : P[Seq[A]]    = a.rep()   // min, max, exactly, separator

  val p5 : P[Option[A]] = a.?       // optionally

  val p6 : P[String]    = a.!       // capture the parsed string. It can be futher converted using `.map`

  val p7 : P[Unit]      = !a | &(a) // Positive or negative look ahead. Checks what is following.


  val p8 : P[B]         = a.map(value => ??? : B)          // conversion

  val p9 : P[B]         = a.flatMap(value => ??? : P[B])   // we can use the previously parsed value in a parser

  val p10: P[A]         = a.filter(value => ??? : Boolean) // only succeed on true value. if false - fail parsing.

  val p11: P[Unit]      = CharPred(_.isUpper)              // parses char if it's true

  val p12: P[Unit]      = CharIn(strings = "123", "abc")   // parses any char from any string

  val p13: P[Unit]      = CharsWhile(_.isLower, min = 1)   // parses at least min characters which are lower

  val p14: P[Unit]      = StringIn(strings = "key", "words")//parses any keyword


}
