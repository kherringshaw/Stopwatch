package edu.luc.etl.cs313.scala.stopwatch
package model

import java.util.{TimerTask, Timer}

/** Contains the components for the event-based clock timer. */
object clock {

  /** A listener for onTick events coming from the internal clock model.  */
  trait OnTickListener { def onTick(): Unit }

  /** The active model of the internal clock that periodically emits tick events. */
  trait ClockModel {
    def start(): Unit
    def stop(): Unit
  }

  /**
   * An implementation of the internal clock.
   * The argument is passed by name for safely setting up a mutual dependency.
   */
  class DefaultClockModel(listener: => OnTickListener) extends ClockModel {

    private val DELAY = 1000

    private var timer: Timer = _

    override def start() = {
      timer = new Timer()
      // The clock model runs onTick every 1000 milliseconds
      timer.schedule(new TimerTask() {
        override def run() = listener.onTick() // fire event
      }, /*initial delay*/ DELAY, /*periodic delay*/ DELAY)
    }

    override def stop() =
      if (timer != null) {
        timer.cancel()
        timer = null
      }
  }
}
