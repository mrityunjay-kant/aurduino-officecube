package com.majikal.web.domain


trait RequestContext {
  def getUserId : String

}

object RequestContext extends ThreadLocal[RequestContext] {
  //this method is specifically for FeedImporter.
  def apply(): RequestContext = new RequestContext {
    def getUserId: String = "MajikalFeedImporter"
  }
}