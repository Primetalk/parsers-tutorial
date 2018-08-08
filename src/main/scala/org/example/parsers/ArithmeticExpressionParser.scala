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

object ArithmeticExpressionParser {
  import fastparse.all._

  val number: P[ArithmeticExpression] = P( CharIn('0' to '9').rep(min = 1).! ).map(_.toInt).map(Number)
  val parens: P[ArithmeticExpression] = P( "(" ~/ addSub ~ ")" )
  val factor: P[ArithmeticExpression] = P( number | parens )

  lazy val divMul: P[ArithmeticExpression] = P( factor ~ (CharIn("*/").! ~/ factor).rep ).map(eval)
  lazy val addSub: P[ArithmeticExpression] = P( divMul ~ (CharIn("+-").! ~/ divMul).rep ).map(eval)
  lazy val expr  : P[ArithmeticExpression] = P( addSub ~ End )

  private def charToOp(char: Char): Operation = char match {
    case '+' => Plus
    case '-' => Minus
    case '*' => Multiply
    case '/' => Divide
    case _   => throw new IllegalArgumentException(s"Cannot convert $char to operation.")
  }

  def eval(pair: (ArithmeticExpression, Seq[(String, ArithmeticExpression)])): ArithmeticExpression = pair match {
    case (x, seq) => seq.foldLeft(x){
      case (a, (operationStr, b)) =>
        BinaryOpApplication(charToOp(operationStr.head), a, b)
    }
  }

}