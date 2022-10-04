package com.ledao.exception;

/**
 * @author wpq
 * @function
 * @Date 2021/12/08
 */
public class NotConfiguredException extends RuntimeException {

  private static final long serialVersionUID = -9040892980028274954L;

  public NotConfiguredException() {
    super();
  }

  public NotConfiguredException(String message) {
    super(message);
  }

  public NotConfiguredException(String message, Throwable cause) {
    super(message, cause);
  }

  public NotConfiguredException(Throwable cause) {
    super(cause);
  }

  protected NotConfiguredException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
