package co.com.company.interpreters

import co.com.company.algebras.{CompanyAlgebra, CompanyAlgebraRepository}
import co.com.company.model.Entities.Company

class CompanyAlgebraInterpreter[F[_]](companyAlgebraRepository: CompanyAlgebraRepository[F]) extends CompanyAlgebra[F]{

  override def save(company: Company): F[Company] = {
    companyAlgebraRepository.save(company)
  }

  override def findAll: fs2.Stream[F,Company] = {
    companyAlgebraRepository.findAll

  }

  override def findByName(companyName: String): F[List[Company]] = {
    companyAlgebraRepository.findCompanyByName(companyName)
  }
}
