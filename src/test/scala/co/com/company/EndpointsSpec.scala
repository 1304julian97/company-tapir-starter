package co.com.company

import co.com.company.Endpoints._
import org.scalatest.EitherValues
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import sttp.client3.testing.SttpBackendStub
import sttp.client3.{UriContext, basicRequest}
import sttp.tapir.server.stub.TapirStubInterpreter

import Library._
import cats.effect.IO
import cats.effect.unsafe.implicits.global
import io.circe.generic.auto._
import sttp.client3.circe._
import sttp.tapir.integ.cats.CatsMonadError

class EndpointsSpec extends AnyFlatSpec with Matchers with EitherValues {

  it should "return hello message" in {
    // given
    val backendStub = TapirStubInterpreter(SttpBackendStub(new CatsMonadError[IO]()))
      .whenServerEndpoint(helloServerEndpoint)
      .thenRunLogic()
      .backend()

    // when
    val response = basicRequest
      .get(uri"http://test.com/hello?name=adam")
      .send(backendStub)

    // then
    response.map(_.body.value shouldBe "Hello adam").unwrap
  }

  it should "list available books" in {
    // given
    val backendStub = TapirStubInterpreter(SttpBackendStub(new CatsMonadError[IO]()))
      .whenServerEndpoint(booksListingServerEndpoint)
      .thenRunLogic()
      .backend()

    // when
    val response = basicRequest
      .get(uri"http://test.com/books/list/all")
      .response(asJson[List[Book]])
      .send(backendStub)

    // then
    response.map(_.body.value shouldBe books).unwrap
  }

  implicit class Unwrapper[T](t: IO[T]) {
    def unwrap: T = t.unsafeRunSync()
  }
}
