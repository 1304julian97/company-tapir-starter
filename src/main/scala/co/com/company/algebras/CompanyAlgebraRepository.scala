package co.com.company.algebras

import co.com.company.model.Entities.Company

trait CompanyAlgebraRepository[F[_]] {

  def save(company: Company): F[Company]

  def save_v2(company: Company): F[Int]

  // TODO don't use lazylist in FP code
  def findAll: fs2.Stream[F,Company]
// if streaming: def findAll: fs2.Stream[F, Company]

  def findCompanyByName(copanyName: String): F[List[Company]]
}
