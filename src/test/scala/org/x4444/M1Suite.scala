package org.x4444

import org.apache.commons.math3.exception.NotPositiveException
import org.scalatest.FunSuite

/**
  * M1Suite
  */
class M1Suite extends FunSuite {

  test("f1") {
    List(1 -> 1, 2 -> 2, 3 -> 6, 20 -> 2432902008176640000L).
      foreach { case (a, b) => assert(M1.f1(a) == b) }

    // test for negative input value
    val thrown = intercept[NotPositiveException] {
      M1.f1(-1)
    }
    assert(thrown.getMessage === "must have n >= 0 for n!, got n = -1")
  }

}
