package advanced

/**
  * Created by Taras on 12/2/2016.
  */
object Headers {

  val headers_common = Map(
    "Accept" -> "application/hal+json, application/json",
    "Connection" -> "keep-alive"
  )

  val cheaders_createAssignment = Map(
    "Accept" -> "application/hal+json, application/json",
    "Content-Type" -> "application/json"
  )

}
