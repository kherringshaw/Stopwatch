package edu.luc.etl.cs313.scala.stopwatch
package model

import common.Constants._

/** Contains the components of the passive time model. */
object time {

  /** The passive data model of the stopwatch. It does not emit any events. */
  trait TimeModel {

    def resetRuntime(): Unit
    def incRuntime(): Unit
    def decRuntime(): Unit
    def getRuntime(): Int
    def setRuntime(value: Int)

    // creation of counter methods to track ticks
    def resetCount(): Unit
    def getCount(): Int
    def incCount(): Unit
  }

  /** An implementation of the stopwatch data model. */
  class DefaultTimeModel extends TimeModel {
    private var runningTime = 0
    private var count=0

    override def resetRuntime() = runningTime = 0
    override def incRuntime()   = runningTime = (runningTime + SEC_PER_TICK)
    override def decRuntime()   = runningTime = (runningTime - SEC_PER_TICK)
    override def getRuntime()   = runningTime
    override def setRuntime(value: Int) = runningTime = value

    //implementation of counter methods to track ticks
    override def resetCount() = count = 0
    override def getCount() = count
    override def incCount() = count = count + 1
  }
}
