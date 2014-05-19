package com.majikal.web.api

import net.liftweb.http._

import net.liftweb.http.rest._
import net.liftweb.common._

import S._


import net.liftweb.common.LazyLoggable
import scala.Some
import com.majikal.web.domain.{CubeException, RequestContext, Response, Error}
import com.majikal.web.domain.CubeErrorCodes._
import Converters._
import net.liftweb.common.BoxOrRaw._


trait RestService extends  RestHelper with LazyLoggable {
  //implicit definition to conver response to string for error response

  implicit def responseToString(r: Response): String = {
    import net.liftweb.json._
    Printer.compact(render(Extraction.decompose(r)))
  }

  //to wrap functions that require session
  def $_! [A](f: => A): BoxOrRaw[Any] = execute(true)(f)

  //to wrap functions that dont require login
  def $ [A](f: => A): BoxOrRaw[Any] = execute(false)(f)


  private def execute [A](requireSession : Boolean)(f: => A): BoxOrRaw[Any] = boxToBoxOrRaw{
    logger.debug("Request received for path: %s".format(request.openOrThrowException("No Request present").path(0)))

    try {


      //TODO add check for access_token here for all API's
      if (requireSession) {

        if(session.isEmpty) {
          throw new CubeException(ERR_NOT_LOGGED_IN, "You must login to complete this action.")
        }
        else {
          val requestUserId = request.openOrThrowException("You must login to complete this action.").param("userId")
          val sessionUserId = getSessionAttribute("userId")

          if(sessionUserId.isEmpty) {
            logger.error("HACK_ATTEMPT: Attempt to create an invalid session on Majikal server, no userId was found even though a valid session context exists, IP address: " + request.get.remoteAddr);
            return boxToBoxOrRaw(Full(Response(Some(List(Error(Some("FATAL_ERROR"), Some(ERR_NO_USER_IN_SESSION), Some("No user was found in session!")))))))
          }

          if(requestUserId == None) {
            logger.warn("No [userId] param in request, userId is a required param for calling this api.");
            return boxToBoxOrRaw(Full(Response(Some(List(Error(Some("CLIENT_ERROR"), Some(ERR_USER_ID_MISSING), Some("No userId was found in request object, [userId] is a mandatory param.")))))))
          }

          if (!sessionUserId.equals(requestUserId.get)) {
            logger.error("HACK_ATTEMPT: Attempt update user info for another user (%s) by user (%s), IP address: %s".format(requestUserId, sessionUserId,  request.get.remoteAddr))
            return boxToBoxOrRaw(Full(Response(Some(List(Error(Some("FATAL_ERROR"), Some(ERR_UNAUTHORIZED_UPDATE), Some("You're not authorized to perform this operation.")))))))
          }
        }
      }


      //set request context per request thread
      RequestContext.set( new RequestContext {
        lazy val getUserId : String = {
          session match {
            case Empty    => " [noSession] "
            case session => " [userId=" + param("userId").openOr("NoUser") + "] "
          }
        }
      })

      logger.debug("Request by user: %s".format(RequestContext.get().getUserId))

      try {
        val resp = f
        resp match {
          case Some(List(Response(Some(List(Error(Some(message), Some(code), Some(description))))))) => {
            Empty ?~ Response(Some(List(Error(Some(message), Some(code), Some(description)))))  ~> errorCode(code)
          }
          case None => {
            logger.debug("Empty Response from Server")
            Empty ?~ "Empty Response from Server" ~> 404
          }
          case _ => {
            println ("Successful Response")
            Full(resp)
          }
        }
      }
      finally {
        //get rid of request context to help JVM for garbage collection
        RequestContext.remove
      }
    }
    catch {
       case me: CubeException => {
        logger.warn("App exception has occurred.", me)
        Empty ?~ Response(Some(List(Error(Some("Error Occurred while Processing Request"), Some(me.code), Some(me.msg))))) ~> errorCode(me.code)

      }
      case x => {
        logger.error("Unknown/Runtime exception has occurred.", x)
        Empty ?~ Response(Some(List(Error(Some("Unknown Error"), Some(ERR_MISC), Some("Some Internal Error occurred at Majikal Server"))))) ~> 500
      }
    }

  }

  private def errorCode (code : Int) = {
    //matching on the error category part from the errorcode
    ((code / 100) % 100 % 10) match {

      case 1 => 500
      case 2 => 401//authentication failure
      case 3 => 400//Bad Request
      case 4 => 404//Resource/Service Not Found
      case 5 => 596
      case 6 => 597
      case 9 => 598
      case _ => 599
    }
  }
  override protected implicit def formats = super.formats //+ new ObjectIdSerializer
}
