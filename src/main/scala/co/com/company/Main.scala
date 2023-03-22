package co.com.company

import cats.effect.{ExitCode, IO, IOApp}
import co.com.company.controllers.CompanyController
import co.com.company.interpreters.{CompanyAlgebraInterpreter, DoobieCompanyAlgebra}
import co.com.company.repositoryConnection.ConnectionImpl
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.server.Router
import sttp.tapir.server.http4s.{Http4sServerInterpreter, Http4sServerOptions}


object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {

    val serverOptions: Http4sServerOptions[IO] =
      Http4sServerOptions
        .customiseInterceptors[IO]
        .metricsInterceptor(Endpoints.prometheusMetrics.metricsInterceptor())
        .options

    val connection = new ConnectionImpl[IO] //TODO remove
    val companyAlgebraRepository = new DoobieCompanyAlgebra[IO](connection.transactorxa)
    val companyAlgebra = new CompanyAlgebraInterpreter[IO](companyAlgebraRepository)
    val contreller = new CompanyController(companyAlgebra)
    val routes = Http4sServerInterpreter[IO](serverOptions).toRoutes(contreller.allEndpoinst)

    val port = sys.env.get("http.port").map(_.toInt).getOrElse(8081)

    BlazeServerBuilder[IO]
      .bindHttp(port, "localhost")
      .withHttpApp(Router("/" -> routes).orNotFound)
      .resource
      .evalTap(server => IO.println(s"Go to http://localhost:${server.address.getPort}/docs to open SwaggerUI. Press ctrl-c to exit."))
      .useForever
  }
}
