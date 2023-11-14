package controllers.greenfossil.jumpstart.day1

import com.greenfossil.thorium.Server
import com.linecorp.armeria.client.WebClient
import com.linecorp.armeria.common.{HttpMethod, HttpRequest, HttpStatus, MediaType}
import munit.FunSuite

class SingUpSuite extends FunSuite:

  /*
   * HINT: Use the `addService` method
   */
  val server: Server = Server(8080).addServices(JumpStartDay1Controller)

  val webClient: WebClient = WebClient.of("http://localhost:8080")

  test("Post to /singup with valid data"):
    val httpRequest = HttpRequest.of(HttpMethod.POST, "/signup", MediaType.FORM_DATA,
      "firstname=Homer&lastname=Simpson&dob=01/05/1990")
    val response = webClient.execute(httpRequest).aggregate().get()
    assertEquals(response.status(), HttpStatus.OK)
    assertNoDiff(response.contentUtf8(), "Welcome Homer Simpson! You were born on 01/05/2023.")

  test("Post to /signup with invalid date"):
    val httpRequest = HttpRequest.of(HttpMethod.POST, "/signup", MediaType.FORM_DATA,
      "firstname=Homer&lastname=Simpson&dob=1990-01-05")
    val response = webClient.execute(httpRequest).aggregate().get()
    assertEquals(response.status(), HttpStatus.BAD_REQUEST)
    assertNoDiff(response.contentUtf8(), "Invalid Data")

  test("Post to /signup without firstname"):
    val httpRequest = HttpRequest.of(HttpMethod.POST, "/signup", MediaType.FORM_DATA,
      "lastname=Simpson&dob=1990-01-05")
    val response = webClient.execute(httpRequest).aggregate().get()
    assertEquals(response.status(), HttpStatus.BAD_REQUEST)
    assertNoDiff(response.contentUtf8(), "Invalid Data")

  test("Post to /singup without lastname"):
    val httpRequest = HttpRequest.of(HttpMethod.POST, "/signup", MediaType.FORM_DATA,
      "firstname=Homer&dob=01/05/1990")
    val response = webClient.execute(httpRequest).aggregate().get()
    assertEquals(response.status(), HttpStatus.OK)
    assertNoDiff(response.contentUtf8(), "Welcome Homer! You were born on 01/05/2023.")

  override def beforeAll(): Unit = server.start()

  override def afterAll(): Unit = server.stop()
