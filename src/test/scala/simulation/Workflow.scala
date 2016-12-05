package simulation

import advanced.{AssignmentManager, Headers, PingThread}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._


class Workflow extends Simulation {

	var url = System.getProperty("mcsApp", "https://gl-maasdam-ics")

	val httpProtocol = http
		.baseURL(url)
		.inferHtmlResources()
		.acceptEncodingHeader("gzip, deflate, sdch")
		.acceptLanguageHeader("en-US,en;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
		.authorizationHeader("Basic Z3JvbWl0OjEyMw==")

	//PingThread.hello.start()
	val scn = scenario("RecordSimulation")//.forever("duration"){
		  .exec(AssignmentManager.desk)
	  //}

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)//.maxDuration(1 minutes)
}