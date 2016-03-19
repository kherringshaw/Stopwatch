package edu.luc.etl.cs313.scala.stopwatch.model

/** A concrete testcase subclass for StatelessBoundedCounter. */
class DefaultStateModelMockitoSpecs extends AbstractStateModelMockitoSpecs {
  override def fixtureSUT(dependencies: Dependencies) =
    new state.DefaultStopwatchStateMachine(
      dependencies.timeModel, dependencies.clockModel, dependencies.uiUpdateListener)
}
