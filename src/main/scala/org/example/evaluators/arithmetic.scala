package org.example.evaluators

import org.example.parsers._

object arithmetic {
  type IntFun = Int => Int
  /**
    * Evaluates the value of an arithmetic expression.
    *  if it's a number, then it's value is the result.
    *  if it's a binary operation, then it's value - is the values of left and right operands with operation applied.
    *
    */
  def eval(functions: Map[Identifier, IntFun] = Map())(e: ArithmeticExpression): Int = e match {
    case Number(value) => value
    case BinaryOpApplication(op, a, b) =>
      val av = eval(functions)(a)
      val bv = eval(functions)(b)
      op match {
        case Multiply => av * bv
        case Divide   => av / bv
        case Plus     => av + bv
        case Minus    => av - bv
      }
    case FunctionApplication1(functionId, argument) =>
      val f = functions.getOrElse(functionId, throw new IllegalArgumentException(s"Function ${functionId.id} not found"))
      val arg = eval(functions)(argument)
      f(arg)
  }

}
