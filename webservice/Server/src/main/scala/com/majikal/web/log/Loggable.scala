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

/**
 * Created with IntelliJ IDEA.
 * User: rpandey
 * Date: 7/3/12
 * Time: 2:58 PM
 * To change this template use File | Settings | File Templates.
 */

trait Loggable {
  /**
   * The logger instance
   */

  protected lazy val logger = LoggerFactory.getLogger(getClass)

  def debug_? = logger.isDebugEnabled
  def info_? = logger.isInfoEnabled
  def warn_? = logger.isWarnEnabled
  def error_? = logger.isErrorEnabled

  /**
   * Log messages using call-by-name to avoid potentially expensive message creation if log level is not enabled
   */

  protected def debug(msg: => AnyRef, t: => Throwable = null):Unit = {
    if(debug_?) {
      if (t != null) {
        logger.debug(msg.toString, t)
      }
      else {
        logger.debug(msg.toString)
      }
    }
  }

  protected def info(msg: => AnyRef, t: => Throwable = null):Unit = {
    if(info_?) {
      if (t != null) {
        logger.info(msg.toString, t)
      }
      else {
        logger.info(msg.toString)
      }
    }
  }

  protected def warn(msg: => AnyRef, t: => Throwable = null):Unit = {
    if(warn_?) {
      if (t != null) {
        logger.warn(msg.toString, t)
      }
      else {
        logger.warn(msg.toString)
      }
    }
  }

  protected def error(msg: => AnyRef, t: => Throwable = null):Unit = {
    if(error_?) {
      if (t != null) {
        logger.error(msg.toString, t)
      }
      else {
        logger.error(msg.toString)
      }
    }
  }
}
