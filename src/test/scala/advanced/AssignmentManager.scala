package advanced

import java.util

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.json.{JSONArray, JSONObject}

/**
  * Created by Taras on 12/2/2016.
  */
object AssignmentManager{
  //remove session variable cause memory leaks
  var assignmentIDS = new util.ArrayList[String]()
  val desk = scenario("Open Assignment Pane")

    .group("Open Assignment pane"){
      exec(http("get all assignments")
        .get("/api/assignment/assignments")
        .headers(Headers.headers_common)
        .check(jsonPath("$").saveAs("myResponse"))
      )
        .exec(session => {
          val maybeId = session.get("myResponse").asOption[String]
          val json = new JSONObject(maybeId.toList.get.head).get("_embedded")
          val idsLength = json.asInstanceOf[JSONObject].get("assignment:assignments").asInstanceOf[JSONArray].length()
          val ids = idsLength - 1
          for (i <- 0 to ids) {
            //println(json.asInstanceOf[JSONObject].get("assignment:assignments").asInstanceOf[JSONArray].get(i).asInstanceOf[JSONObject].get("id"))
            assignmentIDS.add(json.asInstanceOf[JSONObject].get("assignment:assignments").asInstanceOf[JSONArray].get(i).asInstanceOf[JSONObject].get("id").toString)
          }
          session.set("assIDS", assignmentIDS)
        })
        .exec(http("get categories")
          .get("/api/assignment/settings/categories")
          .headers(Headers.headers_common)
        )
        .exec(http("get topics")
          .get("/api/assignment/settings/topics")
          .headers(Headers.headers_common)
        )
        .exec(http("get destinations")
          .get("/api/assignment/settings/destinations")
          .headers(Headers.headers_common)
        )
        .exec(http("get roles")
          .get("/api/assignment/ums/roles")
          .headers(Headers.headers_common)
        )

        .exec(http("get users")
          .get("/api/assignment/ums/users")
          .headers(Headers.headers_common)
        )
    }

    .exec(http("Create new assignment")
      .post("/api/assignment/assignments")
      .headers(Headers.cheaders_createAssignment)
      .body(StringBody("""{"title":"PERFORMANCE-TEST","scheduled":"1476347318564","note":"","assigned":"gromit","createdBy":"gromit","categories":[],"topics":[],"tags":[],"resources":[],"destinations":[], "elements":[]}"""))
      .check(jsonPath("$").saveAs("assignment"))
    )
    .exec(session => {
      val maybeId = session.get("assignment").asOption[String]
      val json = new JSONObject(maybeId.toList.get.head).get("_embedded")
      session.set("assignmentID", json.asInstanceOf[JSONObject].get("assignment:assignments").asInstanceOf[JSONObject].get("id"))
    })
    .exec(http("Populate assignment with data")
      .put("/api/assignment/assignments/${assignmentID}")
      .headers(Headers.cheaders_createAssignment)
      .body(StringBody("""{"title":"","scheduled":"1476347318564","note":"","assigned":"gromit","createdBy":"gromit","categories":[],"topics":[],"tags":[],"resources":[],"destinations":[], "elements":[]}"""))
    )
    .exec(http("Add 20 elements")
      .put("/api/assignment/assignments/${assignmentID}")
      .headers(Headers.headers_common)
      .body(StringBody("""{"title":"","scheduled":"1476347318564","note":"","assigned":"gromit","createdBy":"gromit","categories":[],"topics":[],"tags":[],"resources":[],"destinations":[], "elements":[]}"""))
    )

    .exec(http("Filter by Assigned")
      .get("/api/assignment/assignments")
      .headers(Headers.headers_common)
      .queryParam("assigned", "gromit")
    )
    .exec(http("Filter by Scheduled date")
      .get("/api/assignment/assignments")
      .headers(Headers.headers_common)
      .queryParam("scheduled", "date-date")
    )
    .exec(http("Filter by Search")
      .get("/api/assignment/assignments")
      .headers(Headers.headers_common)
      .queryParam("search", "Assignment+performance+Business+12312+%23Tags")
    )
    .exec(http("Filter by Resources")
      .get("/api/assignment/assignments")
      .headers(Headers.headers_common)
      .queryParam("resources", "${resource.roleID},${resource.userID}")
    )
    .exec(http("Filter by Destinations")
      .get("/api/assignment/assignments")
      .headers(Headers.headers_common)
      .queryParam("destinations", "destinationID")
    ).pause(5)
    .exec(http("Delete created Assignment")
      .delete("/api/assignment/assignments/${assignmentID}")
      .headers(Headers.cheaders_createAssignment)
      .queryParam("destinations", "destinationID")
    )
    .group("Logout"){
      exec(http("Logout")
        .get("/logout")
        .headers(Headers.headers_common)
      )
    }
}