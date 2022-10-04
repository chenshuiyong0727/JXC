package com.ledao.exception;

/**
 * @author wpq
 * @function
 * @Date 2021/12/07
 */
public class ClientUnsupportedAPIException extends RuntimeException {

  private static final long serialVersionUID = 4997268746343048917L;

  public ClientUnsupportedAPIException() {
    super();
  }

  public ClientUnsupportedAPIException(String message) {
    super(message);
  }

  public ClientUnsupportedAPIException(String message, Throwable cause) {
    super(message, cause);
  }

  public ClientUnsupportedAPIException(Throwable cause) {
    super(cause);
  }

  protected ClientUnsupportedAPIException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
