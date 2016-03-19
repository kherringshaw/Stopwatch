package edu.luc.etl.cs313.scala.stopwatch
package model

import common.{StopwatchModel, StopwatchMemento}
import time._
import clock._
import state.{DefaultStopwatchStateMachine, StopwatchStateMachine}

/** A facade implementation of the model interface. */
trait ConcreteStopwatchModelFacade extends StopwatchModel {


  val timeModel: TimeModel   = new DefaultTimeModel
  val clockModel: ClockModel = new DefaultClockModel(stateMachine)
  val stateMachine: StopwatchStateMachine = new DefaultStopwatchStateMachine(timeModel, clockModel, listener)

  // methods in StopwatchUIListener
  override def onStartStop() = stateMachine.onStartStop()
  override def onPopulate(number:Int) = stateMachine.onPopulate(number)


  // methods in Startable
  override def onStart()     = (stateMachine.actionInit())
  override def onStop()      = clockModel.stop()

  override def getMemento() = new StopwatchMemento {
    override val runTime: Int = timeModel.getRuntime
  }

  override def restoreFromMemento(memento: StopwatchMemento): Unit = {
    timeModel.setRuntime(memento.runTime)
    stateMachine.actionUpdateView()
  }
}