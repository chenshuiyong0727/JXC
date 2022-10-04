package com.ledao.exception;

/**
 * @author wpq
 * @function
 * @Date 2021/12/08
 */
public class OpenSigCheckFailException extends RuntimeException {

  private static final long serialVersionUID = 8624799843169837575L;

  public OpenSigCheckFailException() {
    super();
  }

  public OpenSigCheckFailException(String message) {
    super(message);
  }

  public OpenSigCheckFailException(String message, Throwable cause) {
    super(message, cause);
  }

  public OpenSigCheckFailException(Throwable cause) {
    super(cause);
  }

  protected OpenSigCheckFailException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
