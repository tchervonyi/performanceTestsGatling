package advanced

import java.util

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.json.{JSONArray, JSONObject}

/**
  * Created by Taras on 12/2/2016.
  */
object AssignmentManager{
  //TODO remove session variable cause memory leaks
  var assignmentIDS = new util.ArrayList[String]()

  //  val assignmentJSON = jsonFile("assignment.json")

  val desk = scenario("Open Assignment Pane")

    .group("Open Assignment pane"){
      exec(OpenAssignmentPane.getAllAssignments)
        .exec(session => {
          val maybeId = session.get("myResponse").asOption[String]
          val json = new JSONObject(maybeId.toList.get.head).get("_embedded")
          val idsLength = json.asInstanceOf[JSONObject].get("assignment:assignments").asInstanceOf[JSONArray].length()
          val ids = idsLength - 1
          for (i <- 0 to ids) {
            //println(json.asInstanceOf[JSONObject].get("assignment:assignments").asInstanceOf[JSONArray].get(i).asInstanceOf[JSONObject].get("id"))
            assignmentIDS.add(json.asInstanceOf[JSONObject].get("assignment:assignments").asInstanceOf[JSONArray].get(i).asInstanceOf[JSONObject].get("id").toString)
          }
          session.set("assignmentIDS", assignmentIDS)
        })
        .exec(OpenAssignmentPane.getCategories)
        .exec(OpenAssignmentPane.getTopics)
        .exec(OpenAssignmentPane.getDestinations)// TODO need destinations IDS
        .exec(OpenAssignmentPane.getUmsRoles)
        .exec(OpenAssignmentPane.getUsers)
    }
    .exec(AssignmentOperations.create).pause(5)
    .exec(session => {
      val maybeId = session.get("assignment").asOption[String]
      val json = new JSONObject(maybeId.toList.get.head).get("_embedded")
      session.set("assignmentID", json.asInstanceOf[JSONObject].get("assignment:assignments").asInstanceOf[JSONObject].get("id"))
    })

    .exec(AssignmentOperations.populate)// TODO need current date and current date + 1 day
    //    .exec(http("Add 20 elements")
    //      .put("/api/assignment/assignments/${assignmentID}")
    //      .headers(Headers.headers_common)
    //      .body(StringBody(""""""))
    //    )

    .exec(AssignmentFiltering.filterByAssigned)
    //    .exec(http("Filter by Scheduled date")
    //      .get("/api/assignment/assignments")
    //      .headers(Headers.headers_common)
    //      .queryParam("scheduled", "date-date")
    //    ) // TODO need start date and start date + 1 day
    .exec(AssignmentFiltering.filterBySearch)
    .exec(AssignmentFiltering.filterByResource)
    .exec(AssignmentFiltering.filterByDestinations)
    .exec(AssignmentOperations.delete)
    .group("Logout"){
      exec(http("Logout")
        .get("/logout")
        .headers(Headers.headers_common)
      )
    }
}