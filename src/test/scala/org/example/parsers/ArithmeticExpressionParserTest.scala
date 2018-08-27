package org.example.parsers

import fastparse.core.Parsed
import org.example.evaluators.arithmetic
import org.example.evaluators.arithmetic.IntFun
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ArithmeticExpressionParserTest extends FlatSpec {

  import ArithmeticExpressionParser._

  behavior of "ArithmeticExpressionParserTest"

  it should "parse number" in {
    assert(
      number.parse("123") === Parsed.Success(Number(123), 3)
    )
  }

  it should "binary" in {
    assert(
      expr.parse("2*3") === Parsed.Success(BinaryOpApplication(Multiply, Number(2), Number(3)),3)
    )
  }

  def parseExpression(e: String): ArithmeticExpression = {
    expr.parse(e).get.value
  }

  it should "expression" in {
    assert(
      parseExpression("2*(3-4)") ===
        BinaryOpApplication(Multiply, Number(2), BinaryOpApplication(Minus, Number(3), Number(4)))
    )
  }

  it should "expression evaluation" in {
    val ar = parseExpression("((1+1*2)+(3*4*5))/3")
    val res = arithmetic.eval()(ar)
    assert(res === 21)
  }

  def intSqrt(arg: Int): Int =
    math.sqrt(arg.toDouble).toInt

  val functions: Map[Identifier, IntFun] = Map(Identifier("sqrt") -> intSqrt)

  it should "parse simple function call" in {
    val ar = parseExpression("sqrt(16)")
    val res = arithmetic.eval(functions)(ar)
    assert(res === 4)
  }

  it should "parse complex expression with function calls" in {
    val ar = parseExpression("sqrt(4*(2+2))-4")
    val res = arithmetic.eval(functions)(ar)
    assert(res === 0)
  }
}
