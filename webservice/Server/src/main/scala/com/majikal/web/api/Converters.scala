package com.majikal.web.api

import org.joda.time.DateTime
import net.liftweb.http.{Req => Request}
import net.liftweb.common._

object Converters {

  implicit def LongOptionToDoubleOption(inp: Option[Long]): Option[Double] = {
    inp match {
      case None => None
      case Some(x) => Some(x.toDouble)
    }
  }


  implicit def BoxToOption[T](inp: Box[T]): Option[T] = inp.toOption


  implicit def StringToDoubleOption(value: Option[String]) = {
    value match {
      case None => None
      case x => Some(x.get.toDouble)
    }
  }


  implicit def StringToDateTimeOption(value: Option[String]) = {
    value match {
      case None => None
      case x => Some(new DateTime(x.get))
    }
  }


}

