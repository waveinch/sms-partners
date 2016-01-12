package ch.wavein.sms_partners.auth

import javax.inject.Inject

import ch.wavein.sms_partners.models.auth.Auth
import play.api.Configuration
import play.api.mvc._

import scala.concurrent.Future

/**
  * Created by mattia on 12/01/16.
  */

class BasicAuthRequest[A](val auth: Auth, request: Request[A]) extends WrappedRequest[A](request)

class BasicAuthAction @Inject()(configuration: Configuration) extends ActionBuilder[BasicAuthRequest] with ActionRefiner[Request, BasicAuthRequest] {
  val usernameConfig = BasicAuthAction.usernameConfig
  val passwordConfig = BasicAuthAction.passwordConfig

  val username = configuration.getString(usernameConfig)
    .getOrElse(throw new Exception("Basic Auth username not found -> " + usernameConfig))
  val password = configuration.getString(passwordConfig)
    .getOrElse(throw new Exception("Basic Auth username not found -> " + passwordConfig))

  def refine[A](request: Request[A]) = Future.successful {

    val authOpt = request.headers.get("Authorization").map(decodeBasicAuth)

    authOpt match {
      case Some(a)
        if a.username == username && a.password == password => Right(new BasicAuthRequest[A](a, request))
      case _ => Left(Results.Unauthorized.withHeaders("WWW-Authenticate" -> "Basic realm=\"SMS API\""))
    }
  }

  private def decodeBasicAuth(auth: String): Auth = {
    val baStr = auth.replaceFirst("Basic ", "")
    val Array(user, pass) = new String(new sun.misc.BASE64Decoder().decodeBuffer(baStr), "UTF-8").split(":")
    Auth(user, pass)
  }
}

object BasicAuthAction {
  val usernameConfig = "auth.basic.username"
  val passwordConfig = "auth.basic.password"
}