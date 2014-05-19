package com.majikal.web.domain

/**
 * Created by mkant on 5/18/14.
 */
//class DataModel {
//
//}

case class Error( status    : Option[String],
                  errorCode : Option[Int],
                  message   : Option[String]
                  )

case class Response (
                      error : Option[List[Error]]
                      )

class CubeException(val code: Int, val msg: String) extends Exception("CUB" + code + ": " + msg)


case class CubeResponse(action : Int,
                        msg    : String)