package cats
package mtl
package tests

import cats._
import cats.arrow.FunctionK
import cats.data._
import cats.instances.all._
import cats.laws.discipline.SerializableTests
import cats.mtl.instances.listen._
import cats.laws.discipline.arbitrary._
import cats.mtl.laws.discipline._
import org.scalacheck._

class EitherTTests extends BaseSuite {
  implicit val arbFunctionK: Arbitrary[Option ~> Option] =
    Arbitrary(Gen.oneOf(new (Option ~> Option) {
      def apply[A](fa: Option[A]): Option[A] = None
    }, FunctionK.id[Option]))

  {
    implicit val monadLayerControl: MonadLayerControl[EitherTC[Option, String]#l, Option] =
      cats.mtl.instances.eithert.eitherMonadLayerControl[Option, String]
    checkAll("EitherT[Option, String, ?]",
      MonadLayerControlTests[EitherTC[Option, String]#l, Option].monadLayerControl[Boolean, Boolean])
    checkAll("MonadLayerControl[EitherT[Option, String, ?], Option]",
      SerializableTests.serializable(monadLayerControl))
  }

  {
    implicit val applicativeLayerFunctor: ApplicativeLayerFunctor[EitherTC[Option, String]#l, Option] =
      cats.mtl.instances.eithert.eitherApplicativeLayerFunctor[Option, String]
    checkAll("EitherT[Option, String, ?]",
      ApplicativeLayerFunctorTests[EitherTC[Option, String]#l, Option].applicativeLayerFunctor[Boolean, Boolean])
    checkAll("ApplicativeLayerFunctor[EitherT[Option, String, ?], Option]",
      SerializableTests.serializable(applicativeLayerFunctor))
  }

  {
    implicit val functorLayerFunctor: FunctorLayerFunctor[EitherTC[Option, String]#l, Option] =
      cats.mtl.instances.eithert.eitherFunctorLayerFunctor[Option, String]
    checkAll("EitherT[Option, String, ?]",
      FunctorLayerFunctorTests[EitherTC[Option, String]#l, Option].functorLayerFunctor[Boolean])
    checkAll("FunctorLayerFunctor[EitherT[Option, String, ?], Option]",
      SerializableTests.serializable(functorLayerFunctor))
  }
}