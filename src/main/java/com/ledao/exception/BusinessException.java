package com.ledao.exception;

/**
 * @author wpq
 * @function
 * @Date 2021/12/07
 */
public class BusinessException extends RuntimeException {

  private static final long serialVersionUID = -1436412231586463690L;

  public BusinessException(String message) {
    super(message);
  }

  public BusinessException(String message, Throwable cause) {
    super(message, cause);
  }

  public BusinessException() {
    super();
  }

  public BusinessException(Throwable cause) {
    super(cause);
  }

  protected BusinessException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
