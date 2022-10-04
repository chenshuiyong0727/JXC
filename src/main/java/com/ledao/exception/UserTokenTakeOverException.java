package com.ledao.exception;

/**
 * @author wpq
 * @function
 * @Date 2021/12/07
 */
public class UserTokenTakeOverException extends RuntimeException {

  private static final long serialVersionUID = 1054882223557036899L;


  public UserTokenTakeOverException() {
    super();
  }

  public UserTokenTakeOverException(String message) {
    super(message);
  }

  public UserTokenTakeOverException(String message, Throwable cause) {
    super(message, cause);
  }

  public UserTokenTakeOverException(Throwable cause) {
    super(cause);
  }

  protected UserTokenTakeOverException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
