package dp.d3_bahavioral.d10_state

//策略模式通常是封装某种算法
//State 用于根据Subject的状态，将不同状态下的行为动作进行封装，且状态之间自行进行状态的切换。

trait State[T] {
  def press(context: T)
}

object Playing extends State[MediaPlayer] {
  override def press(context: MediaPlayer): Unit = {
    println("Pressing pause.")
    context.setState(Paused)
  }
}

object Paused extends State[MediaPlayer] {
  override def press(context: MediaPlayer): Unit = {
    println("Pressing play.")
    context.setState(Playing)
  }
}

case class MediaPlayer() {
  private var state: State[MediaPlayer] = Paused

  def setState(state: State[MediaPlayer]): Unit = {
    this.state = state
  }

  def pressPlayOrPauseButton(): Unit = {
    state.press(this)
  }
}

object StateDemo {
  def main(args: Array[String]): Unit = {
    val mediaPlayer = new MediaPlayer
    mediaPlayer.pressPlayOrPauseButton()
    mediaPlayer.pressPlayOrPauseButton()
    mediaPlayer.pressPlayOrPauseButton()
    mediaPlayer.pressPlayOrPauseButton()
  }
}
