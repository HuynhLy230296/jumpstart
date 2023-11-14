package controllers.greenfossil.jumpstart.day1

import com.greenfossil.data.mapping.Mapping
import com.greenfossil.data.mapping.Mapping.*
import com.greenfossil.thorium.{*, given}
import com.linecorp.armeria.server.annotation.{Default, Get, Param, Post}

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object JumpStartDay1Controller:

  /*
   * Implement this method to returns:
   * - "Hi, {name}!" if name starts with a vowel
   * - "Hey, {name}!" if name starts with consonant
   * - "Hello stranger!" if name is empty
   */
  @Get("/greetMe")
  def greetMe(@Param @Default("") name: String): String = {
    val trimmedName = name.trim
    val greetingMessage = if (trimmedName.nonEmpty) {
      val firstChar = trimmedName.toLowerCase.head
      if ("aeiou".contains(firstChar))
        s"Hi, $trimmedName!"
      else
        s"Hey, $trimmedName!"
    } else {
      "Hello stranger!"
    }
    greetingMessage
  }

  /*
   * Implement this method to bind to the following fields:
   * 1. firstname : String, mandatory
   * 2. lastname: String, optional
   * 3. dob: java.time.LocalDate with format (dd/MM/yyyy)
   */

  private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

  private def signupMapping: Mapping[(String, Option[String], LocalDate)] = {
    tuple(
      "firstname" -> nonEmptyText,
      "lastname" -> optional(text),
      "dob" -> localDateUsing("dd/MM/yyyy")
    )
  }
  
  /*
   * Implement this method to bind the HTTP request's body to `signupMapping`.
   * If the data mapping has validation errors, return a BadRequest with text "Invalid Data".
   * If has no validation error, return an OK with text
   *    "Welcome {firstname} {lastname}! You were born on {dob<dd/MM/yyy>}."
   */
  @Post("/signup")
  def signup: Action = Action { implicit request =>
    signupMapping.bindFromRequest().fold(
      formWithErrors => BadRequest("Invalid Data"),
      formData => {
        val (firstname, lastname, dob) = formData
        val formattedDob = dob.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        val greeting = if (lastname.isDefined)
          s"Welcome $firstname ${lastname.get}! You were born on $formattedDob."
        else
          s"Welcome $firstname! You were born on $formattedDob."
        Ok(greeting)
      }
    )
  }
