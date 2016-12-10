package com.avid.simulation

import advanced.{AssignmentManager, Config}
import io.gatling.core.Predef._
import io.gatling.http.Predef._


class AssignmentWorkflow extends Simulation {

	val httpProtocol = http
		.baseURL(Config.url)
		.inferHtmlResources()
		.acceptEncodingHeader("gzip, deflate, sdch")
		.acceptLanguageHeader("en-US,en;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
		.authorizationHeader("Basic Z3JvbWl0OjEyMw==")

	val scn = scenario("RecordSimulation").forever("counter"){
		    exec(AssignmentManager.desk)
	}

	setUp(scn.inject(rampUsers(Config.nbUsers) over(Config.rampUp seconds))).protocols(httpProtocol).maxDuration(Config.maxDuration minute)
}