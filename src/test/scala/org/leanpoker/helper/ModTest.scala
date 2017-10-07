package org.leanpoker.helper

import org.scalatest.{FunSpec, MustMatchers}

class ModTest extends FunSpec with MustMatchers{

  it("ads") {
    val x =(5-2) % 4
    println(x)
  }

  it("ads2") {
    val x = (2-5) % 4
    println(x)
  }

}
