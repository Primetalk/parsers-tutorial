package org.example.parsers

import fastparse.core.Parsed
import org.example.evaluators.arithmetic
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

  it should "expression" in {
    assert(
      expr.parse("2*(3-4)") === Parsed.Success(
        BinaryOpApplication(Multiply, Number(2), BinaryOpApplication(Minus, Number(3), Number(4))),
        7)
    )
  }

  it should "expression evaluation" in {
    val e = expr.parse("((1+1*2)+(3*4*5))/3")
    e.fold(
      onFailure = { case (_, pos, extra) =>
        fail(s"at pos $pos, left:$extra")
      },
      onSuccess = {
        case (ar, pos) =>
          val res = arithmetic.eval(ar)
          assert(res === 21)
      }
    )
  }

}
