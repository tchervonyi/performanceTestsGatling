package advanced

/**
  * Created by Taras on 12/10/2016.
  */
object Config {

  var url = System.getProperty("mcsApp", "https://gl-maasdam-ics")
  var nbUsers = Integer.getInteger("users", 1)
  var rampUp = Integer.getInteger("rampUp", 2)
  var maxDuration = Integer.getInteger("maxDuration", 1)


}
