package co.com.company.controllers

import cats.effect.Async
import cats.implicits.catsSyntaxApplicativeId
import co.com.company.algebras.CompanyAlgebra
import co.com.company.model.Entities.Company
import sttp.tapir.endpoint
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe._
import sttp.tapir._
import io.circe.generic.auto._
import sttp.tapir.Codec.string
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.swagger.bundle.SwaggerInterpreter



class CompanyController[F[_]:Async](companyAlgebra: CompanyAlgebra[F]) extends Seriliazation {


  private val saveCompanyEndpoint = endpoint
    .post
    .in("api" / "tapir")
    .in(jsonBody[Company])
    .out(jsonBody[Company])
    .errorOut(stringBody)

  private val listAllEndpoint= endpoint
    .get
    .in("api"/"tapir"/"company")
    .out(jsonBody[List[Company]])
    //.out(streamTextBody(Fs2Streams[F])(CodecFormat.TextPlain(), Some(StandardCharsets.UTF_8)))
    //.out(streamBody(Fs2Streams[F])(Schema.derivedSchema[fs2.Stream[F,Company]],CodecFormat.Json.apply(),Some(StandardCharsets.UTF_8)))
    .errorOut(stringBody)

  private val findCompanyByName = endpoint
    .get
    .in("api"/"tapir"/"company")
    .in(paths)
    .out(jsonBody[List[Company]])
    .errorOut(stringBody)


  private val endpointExampleWithPathParamsAndQueryParamas = endpoint
    .get
    .in("api"/"tapir"/path/"example")
    .in(paths)
    .in(queryParams)
    .out(stringBody)
    .errorOut(stringBody)

  private val saveCompanyServerEndpoint = saveCompanyEndpoint.serverLogicSuccess(c => companyAlgebra.save(c))


  private val listAllServerEndpoint = listAllEndpoint.serverLogicSuccess(_ => companyAlgebra.findAll.compile.toList)

  private val findCompanyByNameServerEndpoint = findCompanyByName.serverLogicSuccess(paths => companyAlgebra.findByName(paths.headOption.getOrElse("")))

  private val endpointExampleWithPathParamsAndQueryParamasServerEndpoint = endpointExampleWithPathParamsAndQueryParamas.serverLogicSuccess( paths => s"PathArgs:  PathInMiddle: ${paths._1} \n PathParams: ${paths._2} \n QueryParams:${paths._3}".pure)

  private val apiEndpoinst: List[ServerEndpoint[Any,F]] = List(saveCompanyServerEndpoint, listAllServerEndpoint, findCompanyByNameServerEndpoint, endpointExampleWithPathParamsAndQueryParamasServerEndpoint)

  private val docEnpoints: List[ServerEndpoint[Any,F]] = SwaggerInterpreter().fromServerEndpoints(apiEndpoinst, "Company","1.1.1")

  val allEndpoinst: List[ServerEndpoint[Any,F]] = apiEndpoinst ++ docEnpoints

}


