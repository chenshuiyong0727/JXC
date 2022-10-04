package com.ledao.model;

/**
 * @author wpq
 * @function 公共校验错误常量类
 * @Date 2021/11/29
 */
public class ServerErrorConst {

  public static final Integer SUCCESS = 1000;
  public static final String SUCCESS_MSG = "操作成功";

  public static final Integer ERR_PARAM_EMPTY = 3000;
  public static final String ERR_PARAM_EMPTY_MSG = "参数为空";

  public static final Integer ERR_TOKEN_EMPTY = 3001;
  public static final String ERR_TOKEN_EMPTY_MSG = "token为空";

  public static final Integer ERR_USER_EMPTY = 3002;
  public static final String ERR_USER_EMPTY_MSG = "用户为空";

  public static final Integer ERR_PARAM_ILLEGAL = 3003;
  public static final String ERR_PARAM_ILLEGAL_MSG = "参数异常";

  public static final Integer ERR_MISSING_HEADER = 3003;
  public static final String ERR_MISSING_HEADER_MSG = "请求头信息缺失";

  public static final Integer ERR_OPERATE_FAIL = 3004;
  public static final String ERR_OPERATE_FAIL_MSG = "操作失败";

  public static final Integer ERR_SIG_CHECKED_FAIL = 3005;
  public static final String ERR_SIG_CHECKED_FAIL_MSG = "签名校验失败";

  public static final Integer ERR_OTHER = 3006;
  public static final String ERR_OTHER_MSG = "服务异常";

  public static final Integer ERR_OPEN_SIG_CHECKED_FAIL = 3007;
  public static final String ERR_OPEN_SIG_CHECKED_FAIL_MSG = "签名校验失败";

  public static final Integer ERR_CLIENT_UNSUPPORTED_API = 3008;
  public static final String ERR_CLIENT_UNSUPPORTED_API_MSG = "Unsupported API for this client.";

  public static final Integer ERR_NOT_CONFIGURED = 3009;
  public static final String ERR_NOT_CONFIGURED_MSG = "配置异常";

  public static final Integer ERR_TOKEN_TAKE_OVER = 3010;
  public static final String ERR_TOKEN_TAKE_OVER_MSG = "用户已在别处登入";

  public static final Integer ERR_HTTP_METHOD_NOT_ALLOW = 405;
  public static final String ERR_HTTP_METHOD_NOT_ALLOW_MSG = "请求方式错误";

  public static final Integer ERR_HTTP_NOT_FOUND = 404;
  public static final String ERR_HTTP_NOT_FOUND_MSG = "资源不存在";

  public static final Integer ERR_HTTP_BAD_REQUEST = 400;
  public static final String ERR_HTTP_BAD_REQUEST_MSG = "错误请求";

  public static final String ERROR_NOT_NULL_MSG = "不能为空";
  public static final char SEPARATOR = ',';
}
