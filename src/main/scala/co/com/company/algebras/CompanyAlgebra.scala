package co.com.company.algebras

import co.com.company.model.Entities.Company

trait CompanyAlgebra [F[_]] {

  def save(company:Company):F[Company]
  def findAll:fs2.Stream[F,Company]
  def findByName(companyName:String):F[List[Company]]
}