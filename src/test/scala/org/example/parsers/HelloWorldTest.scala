package org.example.parsers

import fastparse.all._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec

@RunWith(classOf[JUnitRunner])
class HelloWorldTest extends FlatSpec {

  behavior of "HelloWorld"

  it should "parse hello" in {
    val parsed = HelloWorld.hello.parse("hello")
    assert(parsed === Parsed.Success((), 5))
  }

  it should "parse hello world and return a different result" in {
    val parsed = HelloWorld.helloWorldWithResult.parse("hello world")
    assert(parsed === Parsed.Success("Hello, World!", 11))
  }

  it should "fail parsing misspelled hello" in {
    val parsed = HelloWorld.hello.parse("helo")
    intercept[ParseError](parsed.get)
  }

}
