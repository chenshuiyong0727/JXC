package com.ledao.exception;


import com.ledao.model.ServerErrorConst;

/**
 * @author wpq
 * @function
 * @Date 2021/12/07
 */
public class ServerErrorException extends RuntimeException {

  private static final long serialVersionUID = 7932129171033841055L;

  private Integer errorCode = ServerErrorConst.ERR_OTHER;
  private String message = ServerErrorConst.ERR_OTHER_MSG;

  public ServerErrorException(Integer errorCode, String message) {
    // super("[" + errorCode + "]" + message);
    this.errorCode = errorCode;
    this.message = message;
  }

  public Integer getThisErrorCode() {
    return errorCode;
  }

  public String getThisMessage() {
    return message;
  }
}
