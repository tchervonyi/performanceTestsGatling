package advanced

import java.util

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.json.{JSONArray, JSONObject}

/**
  * Created by Taras on 12/10/2016.
  */
object OpenAssignmentPane {

  def getAllAssignments = http("get all assignments")
    .get("/api/assignment/assignments")
    .headers(Headers.headers_common)
    .check(jsonPath("$").saveAs("myResponse"))

  def getCategories = http("get categories")
    .get("/api/assignment/settings/categories")
    .headers(Headers.headers_common)

  def getTopics = http("get topics")
    .get("/api/assignment/settings/topics")
    .headers(Headers.headers_common)

  def getDestinations = http("get destinations")
    .get("/api/assignment/settings/destinations")
    .headers(Headers.headers_common)

  def getUmsRoles = http("get roles")
    .get("/api/assignment/ums/roles")
    .headers(Headers.headers_common)

  def getUsers = http("get users")
    .get("/api/assignment/ums/users")
    .headers(Headers.headers_common)


}
