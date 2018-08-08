package org.example.parsers

sealed trait ArithmeticExpression

case class Number(value: Int) extends ArithmeticExpression

case class BinaryOpApplication(op: Operation, a: ArithmeticExpression, b: ArithmeticExpression) extends ArithmeticExpression

sealed trait Operation

case object Multiply extends Operation
case object Divide   extends Operation
case object Plus     extends Operation
case object Minus    extends Operation

