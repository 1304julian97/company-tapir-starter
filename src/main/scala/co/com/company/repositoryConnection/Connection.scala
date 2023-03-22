package co.com.company.repositoryConnection

import doobie.util.transactor.Transactor

// TODO delete this, use transactor directly
trait Connection[F[_]] {

  val transactorxa:Transactor[F]
}
