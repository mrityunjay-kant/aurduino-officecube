/*
 * ************************************************************************
 *
 *  ADOBE CONFIDENTIAL
 *  ___________________
 *
 *   Copyright 2013 Adobe Systems Incorporated
 *   All Rights Reserved.
 *
 *  NOTICE:  All information contained herein is, and remains
 *  the property of Adobe Systems Incorporated and its suppliers,
 *  if any.  The intellectual and technical concepts contained
 *  herein are proprietary to Adobe Systems Incorporated and its
 *  suppliers and are protected by trade secret or copyright law.
 *  Dissemination of this information or reproduction of this material
 *  is strictly forbidden unless prior written permission is obtained
 *  from Adobe Systems Incorporated.
 * ************************************************************************
 */

package com.majikal.web.log

import org.slf4j.LoggerFactory
import scala.util.DynamicVariable
import scala.collection.mutable.ListBuffer

/**
 * Created with IntelliJ IDEA.
 * User: rpandey
 * Date: 7/3/12
 * Time: 2:55 PM
 * To change this template use File | Settings | File Templates.
 */

object PerfLogger extends PerfLogger {
  override protected lazy val logger = LoggerFactory.getLogger("PerfLogger")
}

trait PerfLogger extends Loggable {
  protected val _logs   = new DynamicVariable[ListBuffer[String]](new ListBuffer[String])

  def measure[A](msg: => AnyRef)(f: => A) = {

    if (logger.isDebugEnabled) {
      _logs.value.append("!PERFORMANCE! <"+msg.toString+">")
      val t1 = System.nanoTime()
      try
        f
      finally {
        val diff = (System.nanoTime() - t1)/1000000.0
        _logs.value.append("!PERFORMANCE! <startTime>"+t1+"</startTime></"+msg.toString+">")
        _logs.value.append("!PERFORMANCE! <time>"+diff+"ms</time></"+msg.toString+">")
      }
    }
    else
      f
  }

  def collect() {
    if (logger.isDebugEnabled) {
      _logs.value.foreach(debug(_))
    }
    // You dont need to clear this as each thread will get it's own copy but still there is a threadpool that might catch you on wrong footing.
    _logs.value.clear()
  }
}
