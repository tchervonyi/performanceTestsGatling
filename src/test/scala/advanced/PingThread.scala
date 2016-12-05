package advanced

/**
  * Created by Taras on 12/3/2016.
  */
object PingThread {

  val hello = new Thread(new Runnable {
    def run() {
      for(a <- 1 to 10){
        println("hello world")
        Thread.sleep(1000)
      }
    }
  })
}
