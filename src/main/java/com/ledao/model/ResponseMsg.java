package com.ledao.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @param <T> 根据实际情况填入响应实体
 * @author 曾仕斌
 * @function 公共请求响应消息体
 * @date 2021/1/14
 */
@Data
@Slf4j
public class ResponseMsg<T> extends BaseResponse {

  public T data;

  public ResponseMsg(Integer subCode, String subMsg) {
    super(subCode, subMsg);
  }


  public ResponseMsg() {
    super();
    this.setSubCode(ServerErrorConst.SUCCESS);
    this.setSubMsg(ServerErrorConst.SUCCESS_MSG);
  }

  public ResponseMsg(T data) {
    super();
    this.data = data;
    this.setSubCode(ServerErrorConst.SUCCESS);
    this.setSubMsg(ServerErrorConst.SUCCESS_MSG);
  }

  /**
   * 系统级别异常，未调用业务
   */
  public static ResponseMsg createSysErrorResp(Integer returnCode, String msg) {
    ResponseMsg responseMsg = new ResponseMsg();
    responseMsg.setCode(returnCode);
    responseMsg.setMsg(msg);
    return responseMsg;
  }

  /**
   * 业务级系统异常
   */
  public static ResponseMsg createBusinessErrorResp(Integer subCode, String subMsg) {
    ResponseMsg responseMsg = new ResponseMsg();
    responseMsg.setSubCode(subCode);
    responseMsg.setSubMsg(subMsg);
    return responseMsg;
  }

  public ResponseMsg data(T data) {
    this.setData(data);
    return this;
  }

  public T getData() {
    return data;
  }

  public ResponseMsg setData(T data) {
    this.data = data;
    return this;
  }

  public void suc() {
    this.setSubCode(ServerErrorConst.SUCCESS);
    this.setSubMsg(ServerErrorConst.SUCCESS_MSG);
  }

  public void suc(T data) {
    this.setSubCode(ServerErrorConst.SUCCESS);
    this.setSubMsg(ServerErrorConst.SUCCESS_MSG);
    this.setData(data);
  }

  public void err() {
    this.setSubCode(ServerErrorConst.ERR_OPERATE_FAIL);
    this.setSubMsg(ServerErrorConst.ERR_OPERATE_FAIL_MSG);
  }

  public void err(Integer subCode, String subMsg) {
    this.setSubCode(subCode);
    this.setSubMsg(subMsg);
  }

  /**
   * 判断返回结果是否成功且data数据不为空
   */
  public static boolean isSuccessAndHaveBody(ResponseMsg response) {
    boolean result = isSuccess(response);
    if (!result) {
      return result;
    }
    if (null == response.getData()) {
      result = false;
    }
    return result;
  }

  @Override
  public String toString() {
    return "RestResponse{" +
        "data=" + data +
        ", code=" + code +
        ", msg='" + msg + '\'' +
        '}';
  }

  public static ResponseMsg setData(ResponseMsg responseMsg, Object data) {
    if (null == responseMsg || null == data) {
      return responseMsg;
    }
    try {
      if (data instanceof String) {
        responseMsg.setData(data);
      } else {
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        responseMsg.setData(jacksonObjectMapper.writeValueAsString(data));
      }
    } catch (JsonProcessingException e) {
      log.error("ResponseMsg.setData.json.error:", e);
      responseMsg.setData(data);
    }
    return responseMsg;
  }
}
