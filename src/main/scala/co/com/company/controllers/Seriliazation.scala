package co.com.company.controllers


import co.com.company.model.Entities.Company
import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder

trait Seriliazation {

  implicit val companyEncoder:Encoder[Company] = deriveEncoder[Company]

 //   implicit val entityEncoder: Encoder[DTOS.Person] = deriveEncoder[DTOS.Person]

}
