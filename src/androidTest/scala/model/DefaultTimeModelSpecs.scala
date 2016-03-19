package edu.luc.etl.cs313.scala.stopwatch.model

import time.DefaultTimeModel

/** A concrete testcase subclass for the time model. */
class DefaultTimeModelSpecs extends AbstractTimeModelSpecs {
  override def fixture() = new DefaultTimeModel
}
