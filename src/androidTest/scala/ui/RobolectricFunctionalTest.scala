package edu.luc.etl.cs313.scala.stopwatch
package ui

import org.junit.runner.RunWith
import org.robolectric.{Robolectric, RobolectricTestRunner}
import org.scalatest.junit.JUnitSuite

/** Concrete Robolectric test subclass. */
@RunWith(classOf[RobolectricTestRunner])
class RobolectricFunctionalTest extends JUnitSuite with AbstractFunctionalTest {

  override protected lazy val activity =
    Robolectric.buildActivity(classOf[MainActivity]).create().start().get

  override protected def runUiThreadTasks() = Robolectric.runUiThreadTasks()
}
