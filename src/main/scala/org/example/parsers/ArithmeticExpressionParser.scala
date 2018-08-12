package org.example.parsers

import fastparse.CharPredicates.{isDigit, isLetter}

object ArithmeticExpressionParser {
  import fastparse.all._

  /** Number parser.
    *
    *  CharIn('0' to '9') - parses a single digit.
    *
    * `.rep` - parses a sequence (at least `min=1`) of digits.
    *
    * `.map` - applies a conversion to the parsed value.
    */
  val number: P[Number]               = P( CharIn('0' to '9').rep(min = 1).! ).map(_.toInt).map(Number)
  /** An expression in parentheses. */
  val parens: P[ArithmeticExpression] = P( "(" ~/ addSub ~ ")" )
  /** An identifier of a function.
    * It starts with a letter and can contain a few letters or digits afterwards.
    */
  val identifier: P[Identifier]       = P(
    ( CharPred(c => isLetter(c)) ~
      CharPred(c => isLetter(c)
                 || isDigit (c) ).rep).!
  ).map(Identifier)
  val funApp: P[FunctionApplication1] = P( identifier ~ "(" ~/ addSub ~ ")" ).map(FunctionApplication1.tupled)
  /** "Factor" is an atomic element of arithmetic expression. */
  val factor: P[ArithmeticExpression] = P( number | parens | funApp )

  // Parsing basic arithmetic operations
  val plus: P[Operation]              = P( CharIn("+").! ).map(_ => Plus)
  val minus: P[Operation]             = P( CharIn("-").! ).map(_ => Minus)
  val mul: P[Operation]               = P( CharIn("*").! ).map(_ => Multiply)
  val div: P[Operation]               = P( CharIn("/").! ).map(_ => Divide)

  /** We could have parsed a binary operation directly - two factors and an operation in between.
    * If there were a few "*" "/" operations in a row, this would lead to an undesired recursion.
    * So we are going to use another approach utilizing `.rep`.
    */
  lazy val divMul1:P[ArithmeticExpression] = P( factor ~  (mul  | div  ) ~/ factor)
    .map{ case (a, op, b) => BinaryOpApplication(op, a, b) }

  /** Parse arithmetic expression at the "multiplication" level of precedence - "*" and "/" operations
    * are applied in sequence. They have higher precedence than "+", "-".
    *
    * `.rep` returns a sequence of pairs - operation and subfactor.
    * `wrapSeq` - converts sequence of pairs to arithmetic expression.
    */
  lazy val divMul: P[ArithmeticExpression] = P( factor ~ ((mul  | div  ) ~/ factor).rep ).map(foldSeq)
  lazy val addSub: P[ArithmeticExpression] = P( divMul ~ ((plus | minus) ~/ divMul).rep ).map(foldSeq)
  /** A complete expression includes parsing "summation" level of precedence and also the end of the input.
    * Without parsing of an end we could lazily stop processing right after the first factor.
    */
  lazy val expr  : P[ArithmeticExpression] = P( addSub ~ End )

  /** Fold sequence of pairs - operation and arithmetic expression into a single arithmetic expression. */
  def foldSeq(pair: (ArithmeticExpression, Seq[(Operation, ArithmeticExpression)])): ArithmeticExpression = pair match {
    case (x, seq) => seq.foldLeft(x){
      case (a, (operation, b)) =>
        BinaryOpApplication(operation, a, b)
    }
  }

}
