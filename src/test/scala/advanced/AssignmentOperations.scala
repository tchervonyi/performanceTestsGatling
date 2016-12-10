package advanced

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.json.JSONObject

/**
  * Created by Taras on 12/10/2016.
  */
object AssignmentOperations {

  def create = http("Create new assignment")
    .post("/api/assignment/assignments")
    .headers(Headers.cheaders_createAssignment)
    .body(StringBody("""{"title":"PERFORMANCE-TEST","scheduled":"1476347318564","note":"","assigned":"gromit","createdBy":"gromit","categories":[],"topics":[],"tags":[],"resources":[],"destinations":[], "elements":[]}"""))
    .check(jsonPath("$").saveAs("assignment"))

  def populate = http("Populate assignment with data")
    .put("/api/assignment/assignments/${assignmentID}")
    .headers(Headers.cheaders_createAssignment)
    .body(StringBody("""{"title":"Update from performance","scheduled":1481039479187,"note":"","assigned":"gromit","modifiedBy":"gromit","categories":[],"topics":[],"tags":[],"destinations":[],"resources":[],"elements":[]}"""))

  def delete = http("Delete created Assignment")
    .delete("/api/assignment/assignments/${assignmentID}")
    .headers(Headers.cheaders_createAssignment)
    .queryParam("destinations", "destinationID")
}
