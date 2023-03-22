package co.com.company.repositoryConnection
import cats.effect.kernel.Async
import doobie.util.transactor.Transactor

class ConnectionImpl[F[_]: Async] extends Connection[F] {
  // TODO use hikari (connection pool) Resource[F, Transactor[F]]
  override val transactorxa: Transactor[F] = Transactor.fromDriverManager[F](
    "org.postgresql.Driver",
    "jdbc:postgresql:myimdb",
    "docker",
    "docker"
  )
}
