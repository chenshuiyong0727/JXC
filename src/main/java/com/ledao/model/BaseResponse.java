package com.ledao.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * @author 曾仕斌
 * @function 基础返回消息体
 * @date 2021/1/14
 */
@Data
public class BaseResponse implements Serializable {

  /**
   * 系统返回的网关结果，与业务无关，默认1为成功
   */
  protected Integer code = CommonConstant.GW_SUCCESS;

  protected String msg = "SUCCESS";

  /**
   * 业务结果码,returnCode为1时才返回,默认1为成功
   */
  @JsonProperty(value = "sub_code")
  protected Integer subCode;


  /**
   * 业务结果描述,returnCode为1时才返回
   */
  @JsonProperty(value = "sub_msg")
  protected String subMsg;

  public BaseResponse(Integer subCode, String subMsg) {
    this.subCode = subCode;
    this.subMsg = subMsg;
  }

  public BaseResponse() {
  }

  /**
   * 判断返回结果是否成功,成功返回true
   */
  public static boolean isSuccess(BaseResponse response) {
    if (null == response) {
      return false;
    }
    if (null == response.getCode()
        || response.getCode().intValue() != CommonConstant.GW_SUCCESS) {
      return false;
    }
    if (null == response.getSubCode()
        || response.getSubCode().intValue() != CommonConstant.BUSINESS_SUCCESS) {
      return false;
    }
    return true;
  }

  /**
   * 判断返回结果是否成功,成功返回true
   */
  public boolean whetherSuccess() {
    if (null == this) {
      return false;
    }
    if (null == this.getCode()
        || this.getCode().intValue() != CommonConstant.GW_SUCCESS) {
      return false;
    }
    if (null == this.getSubCode()
        || this.getSubCode().intValue() != CommonConstant.BUSINESS_SUCCESS) {
      return false;
    }
    return true;
  }

  /**
   * 判断返回结果是否失败，失败返回true
   */
  public boolean whetherNotSuccess() {
    return !whetherSuccess();
  }

  /**
   * 判断返回结果是否失败，失败返回true
   */
  public static boolean isNotSuccess(BaseResponse response) {
    return !isSuccess(response);
  }

  /**
   * 设置网关结果
   */
  public void setGWResult(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  /**
   * 判断返回结果是否失败，失败返回true
   */
  public static boolean isApiSuccess(BaseResponse response) {
    if (null == response.getCode()
        || response.getCode().intValue() != CommonConstant.GW_SUCCESS) {
      return false;
    }
    return true;
  }

  /**
   * 判断返回结果是否失败，失败返回true
   */
  public static boolean isApiFailed(BaseResponse response) {
    return !isApiSuccess(response);
  }
}
