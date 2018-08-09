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
