package com.majikal.web.api


import net.liftweb.util.BasicTypesHelpers.{AsBoolean, AsInt}
import net.liftweb.http.S._
import com.majikal.web.service.CubeService._


object CubeRestService extends RestService {

 // /cube/arduino
  serveJxa {
   case "cube" :: "arduino" :: _ Get req =>
     $(getArduinoAction)


  }


}
