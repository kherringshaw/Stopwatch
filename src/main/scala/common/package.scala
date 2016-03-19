package edu.luc.etl.cs313.scala.stopwatch

/**
 * Common abstractions used in this app. Kept in a separate package
 * to avoid cycles in the dependency graph.
 */
package object common {

  /** A listener for stopwatch events coming from the UI. */
  trait StopwatchUIListener {
    def onStartStop(): Unit
    def onPopulate(number:Int): Unit
  }

  /**
   * A listener for UI update notifications.
   * This interface is typically implemented by the adapter, with the
   * notifications coming from the model.
   */
  trait StopwatchUIUpdateListener {
    def updateTime(timeValue: Int): Unit
    def updateState(stateId: Int): Unit
    def playSound(): Unit
    //def onPop(): Unit
  }

  /** A component with a start/stop lifecycle tied to the app. */
  trait Startable {
    def onStart(): Unit
    def onStop(): Unit
  }

  /**
   * A source for UI update notifications,
   * with a dependency on the corresponding listener.
   */
  trait StopwatchUIUpdateSource {
    protected val listener: StopwatchUIUpdateListener
  }

  /** The unified interface of the stopwatch model. */
  trait StopwatchModel
  extends StopwatchUIListener with StopwatchUIUpdateSource with Startable {
    def getMemento(): StopwatchMemento
    def restoreFromMemento(memento: StopwatchMemento)
  }

  /** A memento of the stopwatch state. */
  trait StopwatchMemento extends Serializable {
    val runTime: Int
  }
}
