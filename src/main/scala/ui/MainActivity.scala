package edu.luc.etl.cs313.scala.stopwatch
package ui

import java.io.IOException

import android.app.Activity
import android.media.MediaPlayer.OnCompletionListener
import android.media.{AudioManager, MediaPlayer, RingtoneManager}
import android.os.Bundle
import android.text.{Editable, TextWatcher}
import android.util.Log
import android.view.View
import android.widget.{Button, EditText}
import common._
import edu.luc.etl.cs313.scala.stopwatch.model.ConcreteStopwatchModelFacade


/**
 * The main Android activity, which provides the required lifecycle methods.
 * By mixing in the abstract Adapter behavior, this class serves as the Adapter
 * in the Model-View-Adapter pattern. It connects the Android GUI view with the
 * model. The model implementation is configured externally via the resource
 * R.string.model_class.
 */
class MainActivity extends Activity with TypedActivity with StopwatchUIUpdateListener {

  private val TAG = "stopwatch-android-activity"


  /**
   * The model on which this activity depends. The model also depends on
   * this activity; we inject this dependency using abstract member override.
   */
  private val model: StopwatchModel = new ConcreteStopwatchModelFacade {
    lazy val listener = MainActivity.this
  }

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    Log.i(TAG, "onCreate")
    // inject the (implicit) dependency on the view
    setContentView(R.layout.main)
  }

  override def onStart() {
    super.onStart()
    Log.i(TAG, "onStart")
    model.onStart()
  }

  private val KEY = "stopwatch-memento"

  // TODO restore complete clock state on rotation

  override def onSaveInstanceState(savedInstanceState: Bundle): Unit = {
    Log.i(TAG, "onSaveInstanceState")
    model.onStop()
    savedInstanceState.putSerializable(KEY, model.getMemento)
    super.onSaveInstanceState(savedInstanceState)
  }

  override def onRestoreInstanceState(savedInstanceState: Bundle): Unit = {
    super.onRestoreInstanceState(savedInstanceState)
    Log.i(TAG, "onRestoreInstanceState")
    model.restoreFromMemento(savedInstanceState.getSerializable(KEY).asInstanceOf[StopwatchMemento])
  }

  /**
   * Forwards the semantic ``onStartStop`` event to the model.
   * (Semantic as opposed to, say, a concrete button press.)
   * This and similar events are connected to the
   * corresponding onClick events (actual button presses) in the view itself,
   * usually with the help of the graphical layout editor; the connection also
   * shows up in the XML source of the view layout.
   */
  def onStartStop(view: View) = model.onStartStop()


  def onPopulate(view: View) = {
    var number = Integer.parseInt(this.findViewById(R.id.typedDigits).asInstanceOf[EditText].getText().toString())
    model.onPopulate(number)
    findView(TR.typedDigits).setText("")

  }


  /** Wraps a block of code in a Runnable and runs it on the UI thread. */
  def runOnUiThread(block: => Unit): Unit =
    runOnUiThread(new Runnable() { override def run(): Unit = block })

  /**
   * Updates the seconds in the UI. It is this UI adapter's
   * responsibility to schedule these incoming events on the UI thread.
   */
  def updateTime(time: Int): Unit = runOnUiThread {
    val tvS = findView(TR.seconds)
    val seconds = time % Constants.TIMER
    tvS.setText((seconds / 10).toString + (seconds % 10).toString)
    findView(TR.startStop).setEnabled(seconds < 10)

  }

  /**
   * Updates the state name shown in the UI. It is this UI adapter's
   * responsibility to schedule these incoming events on the UI thread.
   */
  def updateState(stateId: Int): Unit = runOnUiThread {
    val stateName = findView(TR.stateName)
    stateName.setText(getString(stateId))
  }

  /** Plays the default notification sound. */
  def playSound(): Unit = {
    val defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val mediaPlayer = new MediaPlayer

    try {
      mediaPlayer.setDataSource(getApplicationContext, defaultRingtoneUri)
      mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION)
      mediaPlayer.prepare()
      mediaPlayer.setOnCompletionListener(new OnCompletionListener {
        override def onCompletion(mp: MediaPlayer): Unit = { mp.release() }
      })
      mediaPlayer.start()
    } catch {
      case ex: IOException =>  throw new RuntimeException(ex)
    }
  }

  /*def onPop(): Unit ={
    val textInput = this.findViewById(R.id.typedDigits).asInstanceOf[EditText]
    val numberInput = this.findViewById(R.id.seconds).asInstanceOf[EditText]
    val number_value = numberInput.getText.toString
    textInput.setText(number_value)

  }*/

  /**populate=findViewById(R.id.buttons2).asInstanceOf[Button]
  populate.setOnClickListener(new View.OnClickListener {
    override def onClick(v: View){
      val num_val = input.getText.toString

    }
  })*/


  }

