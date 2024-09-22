package mock

import scala.language.postfixOps
import com.intuit.karate.gatling.PreDef._
import io.gatling.core.Predef._
import scala.concurrent.duration._

class CatsKarateSimulation extends Simulation {

  MockUtils.startServer()

  // parse load profile from Taurus
  val t_concurrency = Integer.getInteger("concurrency", 10).toInt
  val t_holdFor = Integer.getInteger("hold-for", 60).toInt

  val feeder = Iterator.continually(Map("catName" -> MockUtils.getNextCatName))

  val protocol = karateProtocol(
    "/cats/{id}" -> Nil,
    "/cats" -> pauseFor("get" -> 15, "post" -> 25)
  )

  protocol.nameResolver = (req, ctx) => req.getHeader("karate-name")

  val create = scenario("create").feed(feeder).exec(karateFeature("classpath:mock/cats-create.feature"))
  val delete = scenario("delete").group("delete cats") {
    exec(karateFeature("classpath:mock/cats-delete.feature@name=delete"))
  }
  val custom = scenario("custom").exec(karateFeature("classpath:mock/custom-rpc.feature"))

  setUp(
    create.inject(constantUsersPerSec(t_concurrency) during (t_holdFor seconds)).protocols(protocol),
    delete.inject(constantUsersPerSec(t_concurrency) during (t_holdFor seconds)).protocols(protocol)
  )

}
