package co.com.company.interpreters

import cats.effect.Concurrent
import cats.syntax.all._
import co.com.company.algebras.CompanyAlgebraRepository
import co.com.company.model.Entities
import doobie.implicits._
import doobie.util.transactor.Transactor
import doobie.util.update.Update

// TODO add second implementation using skunk instead of doobie
class DoobieCompanyAlgebra[F[_]: Concurrent](xa: Transactor[F]) extends CompanyAlgebraRepository[F] {
  import DoobieCompanyAlgebra.queries
  override def save(company: Entities.Company): F[Entities.Company] =
    queries
      .insertCompany(company)
      .run
      .transact(xa)
      .as(company)

  override def findAll: fs2.Stream[F,Entities.Company] = queries.findAll.stream.transact(xa)


  override def findCompanyByName(companyName: String): F[List[Entities.Company]] =
    queries.findCompanyByName(companyName).to[List].transact(xa)


  override def save_v2(company: Entities.Company): F[Int] = {
    val query = "INSERT INTO companies (nit, name, year_of_creation, ceo_id) VALUES (?,?,?,?);"
    Update[Entities.Company](query).run(company).transact(xa)
  }
}

object DoobieCompanyAlgebra {
  private[interpreters] object queries {
    // All methods should return Query0 or Update0

    // TODO unit test using `check(insertCompany(...))`
    // check doobie documentation book chapter 13
    def insertCompany(company: Entities.Company): doobie.Update0 = {
      sql"INSERT INTO companies (nit, name, year_of_creation, ceo_id) VALUES (${company.nit},${company.name},${company.year},${company.CEOId})".update
    }

   def findCompanyByName(companyName:String): doobie.Query0[Entities.Company] = {
     val finalArgument = s"%$companyName%"
     sql"select * from companies where name like $finalArgument".query[Entities.Company]
   }

    def findAll: doobie.Query0[Entities.Company] = {
    // TODO don't select *
    /*
    The reason behind dont use * is since futures modifications,
    * could cause a bad conversion if the table is updated
    In addition to that, in real context, this query is not common, in general we always use filter order or something similar.
    * could bring fields that we do not use.
    In this case it is used only for learning purposes.
     */
      sql"select * from companies".query[Entities.Company]
    }

  }
}
