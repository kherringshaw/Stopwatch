package edu.luc.etl.cs313.scala.stopwatch
package model

import org.junit.Test
import org.mockito.Mockito._
import org.mockito.Matchers._
import org.scalatest.junit.JUnitSuite
import org.scalatest.mock.MockitoSugar
import ui.R
import common.StopwatchUIUpdateListener
import clock.ClockModel
import state.StopwatchStateMachine
import time.TimeModel

/**
 * An abstract unit test for the state machine abstraction.
 * This is a unit test of an object with multiple dependencies;
 * we use a unified mock object to satisfy all dependencies
 * and verify that the state machine behaved as expected.
 * This also follows the XUnit Testcase Superclass pattern.
 */
trait AbstractStateModelMockitoSpecs extends JUnitSuite with MockitoSugar {

  trait Dependencies {
    val timeModel: TimeModel
    val clockModel: ClockModel
    val uiUpdateListener: StopwatchUIUpdateListener
  }

  /** Creates an instance of the SUT. */
  def fixtureSUT(dependencies: Dependencies): StopwatchStateMachine

  /** Creates instances of the dependencies. */
  def fixtureDependency() = new Dependencies {
    override val timeModel = mock[TimeModel]
    override val clockModel = mock[ClockModel]
    override val uiUpdateListener = mock[StopwatchUIUpdateListener]
  }

  /**
   * Verifies that we're initially in the stopped state (and told the listener
   * about it).
   */
  @Test def testPreconditions() = {
    val dep = fixtureDependency()
    val model = fixtureSUT(dep)
    model.actionInit()
    verify(dep.uiUpdateListener).updateState(R.string.STOPPED)
    verify(dep.uiUpdateListener).updateTime(0)
    verifyNoMoreInteractions(dep.uiUpdateListener, dep.clockModel)
  }

  /**
   * Verifies the following scenario: time is 0, press start, wait 5+ seconds,
   * expect time 5.
   */
  @Test def testScenarioRun(): Unit = {
    val dep = fixtureDependency()
    val model = fixtureSUT(dep)
    val t = 5
    model.actionInit()
    verify(dep.clockModel, never).start()
    verify(dep.timeModel, never).incRuntime()
    verify(dep.uiUpdateListener).updateState(R.string.STOPPED)
    // directly invoke the button press event handler methods
    model.onStartStop()
    verify(dep.clockModel).start()
    verify(dep.uiUpdateListener).updateState(R.string.RUNNING)
    (1 to t) foreach { _ => model.onTick() }
    verify(dep.timeModel, times(t)).incRuntime()
    verify(dep.uiUpdateListener, times(t + 1)).updateTime(anyInt)
    verifyNoMoreInteractions(dep.clockModel)
  }

  /**
   * Verifies the following scenario: time is 0, press start, wait 5+ seconds,
   * expect time 5, press lap, wait 4 seconds, expect time 5, press start,
   * expect time 5, press lap, expect time 9, press lap, expect time 0.
   */
  @Test def testScenarioRunLapReset(): Unit = {
    val dep = fixtureDependency()
    val model = fixtureSUT(dep)
    val t1 = 5
    val t2 = 9
    model.actionInit()
    verify(dep.clockModel, never).start()
    verify(dep.clockModel, never).stop()
    verify(dep.uiUpdateListener).updateState(R.string.STOPPED)
    verify(dep.timeModel, never).incRuntime()
    verify(dep.uiUpdateListener).updateTime(0)
    when(dep.timeModel.getRuntime).thenReturn(t1)
    // directly invoke the button press event handler methods on model
    model.onStartStop()
    verify(dep.clockModel).start()
    verify(dep.uiUpdateListener).updateState(R.string.RUNNING)
    (1 to t1) foreach { _ => model.onTick() }
    verify(dep.timeModel, times(t1)).incRuntime()
    verify(dep.uiUpdateListener, times(t1)).updateTime(t1)
    when(dep.timeModel.getRuntime).thenReturn(t2)
     (t1 + 1 to t2) foreach { _ => model.onTick() }
    verify(dep.timeModel, times(t2)).incRuntime()
    verify(dep.uiUpdateListener, times(t2)).updateTime(t1)
    model.onStartStop()
    verify(dep.clockModel).stop()
       verify(dep.timeModel, times(t2)).incRuntime()
    when(dep.timeModel.getRuntime).thenReturn(0)
    verify(dep.uiUpdateListener, times(2)).updateState(R.string.STOPPED)
    verify(dep.uiUpdateListener, times(2)).updateTime(0)
    verifyNoMoreInteractions(dep.clockModel)
  }
}
