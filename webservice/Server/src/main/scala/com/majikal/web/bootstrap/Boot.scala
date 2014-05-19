package com.majikal.web.bootstrap


import net.liftweb.http.{Bootable, Html5Properties, LiftRules, Req}
import net.liftweb.sitemap.{Menu, SiteMap}
import net.liftweb.scalate.ScalateView

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */

import com.majikal.web._

class Boot extends Bootable {

  def boot {
    // where to search snippet
    LiftRules.addToPackages("com.majikal.web")
    LiftRules.dispatch.append(api.CubeRestService)

    val scalateView = new ScalateView
    scalateView.register

  }
}