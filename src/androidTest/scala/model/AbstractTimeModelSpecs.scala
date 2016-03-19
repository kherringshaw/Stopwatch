package edu.luc.etl.cs313.scala.stopwatch
package model

import org.junit.Assert._
import org.junit.Test
import org.scalatest.junit.JUnitSuite
import common.Constants._
import time.TimeModel

/**
 * An abstract unit test for the time model abstraction.
 * This is a simple unit test of an object without dependencies.
 * This follows the XUnit Testcase Superclass pattern.
 */
trait AbstractTimeModelSpecs extends JUnitSuite {

  def fixture(): TimeModel

  /** Verifies that runtime and laptime are initially 0 or less. */
  @Test def testPreconditions(): Unit = {
    val model = fixture()
    assertEquals(0, model.getRuntime)
    assertTrue(model.getCount() == 0)//verifies that count starts a 0
  }

  /** Verifies that runtime is incremented correctly. */
  @Test def testIncrementRuntimeOne(): Unit = {
    val model = fixture()
    val rt = model.getRuntime
    model.incRuntime()
    assertEquals((rt + SEC_PER_TICK) % TIMER, model.getRuntime)

  }

  /** Verifies that runtime is incremented correctly. */
  @Test def testDecrementRuntimeOne(): Unit = {
    val model = fixture()
    val rt = model.getRuntime
    model.decRuntime()
    assertEquals((rt - SEC_PER_TICK) % TIMER, model.getRuntime)

  }



}
