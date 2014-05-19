package com.majikal.web.domain

/**
 * Created by mkant on 5/18/14.
 */
object CubeErrorCodes {

  //*
  //Errors due to missing or bad configuration
  //*
  val ERR_HTTP_NOT_SUPPORTED = 100100
  //  val ERR_MAJIKAL_APP_POST_COMMENT_SOCIAL = 100401
  val ERR_SN_NOT_SUPPORTED_LOGIN = 100402
  val ERR_NO_USER_IN_SESSION = 100299
  val ERR_UNAUTHORIZED_UPDATE = 100298  //attempt to update user data other than own data

  //*
  //Errors due to authentication failure
  //*
  //SubModule: API Params
  val ERR_USER_ID_MISSING = 100300
  val ERR_EMAIL_ID_MISSING = 100301
  val ERR_PASSWORD_MISSING = 100302
  val ERR_ACCESS_TOKEN_MISSING = 100303
  val ERR_SN_MISSING = 100304
  val ERR_MISSING_LOGIN_PARAMS = 100305
  val ERR_NOT_LOGGED_IN = 100200
  val ERR_ACCESS_REVOKED_FB = 100201
  val ERR_UNKNOWN_ERROR_FB_REAUTH = 100202
  val ERR_NETOWRK_NOT_CONNECTED = 100306

  val ERR_MISC = 100900

}
