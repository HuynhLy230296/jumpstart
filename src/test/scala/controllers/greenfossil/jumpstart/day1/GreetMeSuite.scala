package controllers.greenfossil.jumpstart.day1

import com.greenfossil.thorium.Server
import com.linecorp.armeria.client.WebClient
import com.linecorp.armeria.common.{HttpStatus, QueryParams}
import munit.FunSuite

class GreetMeSuite extends FunSuite:

  /*
   * HINT: Use the `addService` method
   */
  val server: Server = Server(8080).addServices(JumpStartDay1Controller)

  val webClient: WebClient = WebClient.of("http://localhost:8080")

  test("Get /greetMe with name that starts with a vowel"):
    val params = QueryParams.of("name", "Abraham")
    val response = webClient.get("/greetMe", params).aggregate.get()
    assertEquals(response.status(), HttpStatus.OK)
    assertNoDiff(response.contentUtf8(), "Hi, Abraham!")

  test("Get /greetMe with name that starts with a consonant"):
    val params = QueryParams.of("name", "Mona")
    val response = webClient.get("/greetMe", params).aggregate.get()
    assertEquals(response.status(), HttpStatus.OK)
    assertNoDiff(response.contentUtf8(), "Hey, Mona!")

  test("Get /greetMe with empty name"):
    val response = webClient.get("/greetMe").aggregate.get()
    assertEquals(response.status(), HttpStatus.OK)
    assertNoDiff(response.contentUtf8(), "Hello stranger!")

  override def beforeAll(): Unit = server.start()

  override def afterAll(): Unit = server.stop()