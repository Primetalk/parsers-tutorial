package org.example.parsers

import fastparse.all._
import org.specs2.mutable.Specification

class HelloWorldTest extends Specification {

  "HelloWorld" should {
    "parse hello" in {
      val parsed = HelloWorld.hello.parse("hello")
      assert(parsed === Parsed.Success((), 5))
      ok
    }

    "parse hello world and return a different result" in {
      val parsed = HelloWorld.helloWorldWithResult.parse("hello world")
      assert(parsed === Parsed.Success("Hello, World!", 11))
      ok
    }

    "fail parsing misspelled hello" in {
      val parsed = HelloWorld.hello.parse("helo")
      parsed.get should throwA[ParseError]
      ok
    }
  }
}
