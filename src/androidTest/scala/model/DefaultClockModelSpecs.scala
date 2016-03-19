package edu.luc.etl.cs313.scala.stopwatch
package model

import clock._

/** A concrete testcase subclass for StatelessBoundedCounter. */
class DefaultClockModelSpecs extends AbstractClockModelSpecs {
  override def fixture(listener: OnTickListener) = new DefaultClockModel(listener)
}
