package advanced

import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by Taras on 12/10/2016.
  */
object AssignmentFiltering {

  def filterByAssigned = http("Filter by Assigned")
    .get("/api/assignment/assignments")
    .headers(Headers.headers_common)
    .queryParam("assigned", "gromit")

  def filterBySearch = http("Filter by Search")
    .get("/api/assignment/assignments")
    .headers(Headers.headers_common)
    .queryParam("search", "Assignment+performance+Business+12312+%23Tags")

  def filterByResource = http("Filter by Resources")
    .get("/api/assignment/assignments")
    .headers(Headers.headers_common)
    .queryParam("resources", "${resource.roleID},${resource.userID}")

  def filterByDestinations = http("Filter by Destinations")
    .get("/api/assignment/assignments")
    .headers(Headers.headers_common)
    .queryParam("destinations", "destinationID")

}
