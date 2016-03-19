package edu.luc.etl.cs313.scala.stopwatch
package ui

import android.widget.{Button, TextView}
import org.junit.Assert._
import org.junit.Test
import common.Constants.TIMER

/**
 * Abstract GUI-level test superclass of several essential stopwatch scenarios.
 * This follows the XUnit Testcase Superclass pattern.
 */
trait AbstractFunctionalTest {

  /** The activity to be provided by concrete subclasses of this test. */
  protected def activity(): MainActivity

  @Test def testActivityExists(): Unit = assertNotNull(activity)

  @Test def testActivityInitialValue(): Unit = assertEquals(0, getDisplayedValue)

  /**
   * Verifies the following scenario: time is 0, press start, wait 5+ seconds, expect time 5.
   *
   * @throws Throwable
   */
  @Test def testActivityScenarioRun(): Unit = {
    activity.runOnUiThread {
      assertEquals(0, getDisplayedValue)
      assertTrue(getStartStopButton().performClick())
    }
    Thread.sleep(5500) // <-- do not run this in the UI thread!
    runUiThreadTasks()
    activity.runOnUiThread {
      assertEquals(5, getDisplayedValue)
      assertTrue(getStartStopButton.performClick())
    }
  }

  /**
   * Verifies the following scenario: time is 0, press start, wait 5+ seconds,
   * expect time 5, press lap, wait 4 seconds, expect time 5, press start,
   * expect time 5, press lap, expect time 9, press lap, expect time 0.
   *
   * @throws Throwable
   */
  /*@Test def testActivityScenarioRunLapReset(): Unit = {
    activity.runOnUiThread {
      assertEquals(0, getDisplayedValue)
      assertTrue(getStartStopButton.performClick())
    }
    Thread.sleep(5500) // <-- do not run this in the UI thread!
    runUiThreadTasks()
    activity.runOnUiThread {
      assertEquals(5, getDisplayedValue)
      assertTrue(getResetLapButton.performClick())
    }
    Thread.sleep(4000) // <-- do not run this in the UI thread!
    runUiThreadTasks()
    activity.runOnUiThread {
      assertEquals(5, getDisplayedValue)
      assertTrue(getStartStopButton.performClick())
    }
    runUiThreadTasks()
    activity.runOnUiThread {
      assertEquals(5, getDisplayedValue)
      assertTrue(getResetLapButton.performClick())
    }
    runUiThreadTasks()
    activity.runOnUiThread {
      assertEquals(9, getDisplayedValue)
      assertTrue(getResetLapButton.performClick())
    }
    runUiThreadTasks()
    activity.runOnUiThread {
      assertEquals(0, getDisplayedValue)
    }
  }*/

  // auxiliary methods for easy access to UI widgets

  protected def tvToInt(t: TextView): Int = t.getText.toString.trim.toInt

  def getDisplayedValue(): Int = {
    val ts = activity.findView(TR.seconds)

    tvToInt(ts)
  }

  protected def getStartStopButton(): Button = activity.findView(TR.startStop)


  /**
   * Explicitly runs tasks scheduled to run on the UI thread in case this is required
   * by the testing framework, e.g., Robolectric. When this is not required,
   * it should be overridden with an empty method.
   */
  protected def runUiThreadTasks(): Unit
}
