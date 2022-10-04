package com.ledao.exception;

import com.ledao.model.BaseMsg;

/**
 * 框架服务异常
 *
 * @author Parker
 * @date 2020-09-13 19:41
 */
public class ServiceException extends RuntimeException {

  private Integer code;

  private String errorMessage;

  public ServiceException(Integer code, String errorMessage) {
    super(errorMessage);
    this.code = code;
    this.errorMessage = errorMessage;
  }

  public ServiceException(Integer code, String errorMessage, Throwable e) {
    super(errorMessage, e);
    this.code = code;
    this.errorMessage = errorMessage;
  }

  public ServiceException(BaseMsg msg) {
    super(msg.getMessage());
    this.code = msg.getCode();
    this.errorMessage = msg.getMessage();
  }

  public ServiceException(BaseMsg msg, Throwable e) {
    super(msg.getMessage(), e);
    this.code = msg.getCode();
    this.errorMessage = msg.getMessage();
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }


}
