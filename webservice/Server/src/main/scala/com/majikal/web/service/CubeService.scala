package com.majikal.web.service

import com.majikal.web.domain.CubeResponse

/**
 * Created by mkant on 5/18/14.
 */
object CubeService {

  def getArduinoAction() : CubeResponse = {
    CubeResponse(123, "test response!")
  }

}
