package org.example.parsers

import fastparse.core.Parsed
import org.example.evaluators.arithmetic
import org.specs2.mutable.Specification

class ArithmeticExpressionParserTest extends Specification {

  import ArithmeticExpressionParser._
  "ArithmeticExpressionParserTest" should {
    "numberExpr" in {
      assert(
        number.parse("123") === Parsed.Success(Number(123), 3)
      )
      ok
    }

    "binary" in {
      assert(
        expr.parse("2*3") === Parsed.Success(BinaryOpApplication(Multiply, Number(2), Number(3)),3)
      )
      ok
    }

    "expression" in {
      assert(
        expr.parse("2*(3-4)") === Parsed.Success(
          BinaryOpApplication(Multiply, Number(2), BinaryOpApplication(Minus, Number(3), Number(4))),
          7)
      )
      ok
    }

    "expression evaluation" in {
      val e = expr.parse("((1+1*2)+(3*4*5))/3")
      e.fold(
        onFailure = { case (_, pos, extra) =>
          failure(s"at pos $pos, left:$extra")
        },
        onSuccess = {
          case (ar, pos) =>
            val res = arithmetic.eval(ar)
            assert(res === 21)
        }
      )
      ok
    }

  }
}
