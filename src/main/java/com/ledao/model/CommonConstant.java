package com.ledao.model;

/**
 * @author 曾仕斌
 * @function 公共常量接口
 * @date 2021/2/4
 */
public interface CommonConstant {

  /**
   * 成功
   */
  public final static int GW_SUCCESS = 1;


  /**
   * 成功
   */
  public final static int BUSINESS_SUCCESS = 1000;

  /**
   * 失败
   */
  public final static int FAILED = 0;

  /**
   * 正常状态
   */
  public final static int STATUS_NORMAL = 1;

  /**
   * 成功
   */
  public final static String SUCCESS_STR = "SUCCESS";

  /**
   * 默认数据源
   */
  public final static String DEFAULT_DATA_SOURCE = "DEFAULT";

  /**
   * 公共数据源
   */
  public final static String COMMON_DATA_SOURCE = "COMMON";

  /**
   * 数据源前缀
   */
  public final static String DATA_SOURCE_PREFIX = "DB_";

  final static String USER_ID = "userId";

  final static String USER_REAL_NAME = "userRealName";

  /**
   * 日志跟中编号
   */
  final static String TRACE_ID = "traceId";

  /**
   * 用户账户
   */
  final static String USER_ACCOUNT = "userAccount";


  /**
   * 用户客户编号
   */
  final static String CUSTOMER_ID = "customerId";

  /**
   * 用户客户编号
   */
  final static String ACCOUNT_ID = "accountId";

  /**
   * 用户账户信息
   */
  final static String KEY_USERINFO_IN_HTTP_HEADER = "X-AUTO-FP-USERINFO";

  /**
   * 请求编号
   */
  final static String REQUEST_ID = "requestId";

  /**
   * 注册完成
   */
  final static String REGISTRATION_COMPLETE = "registration_complete";

  /**
   * 快筛
   */
  final static String RAPID_SCREENING = "rapid_screening";

  /**
   * 评估
   */
  final static String ASSESSMENT = "assessment";

  /**
   * 到院诊断
   */
  final static String DIAGNOSIS = "diagnosis";

  /**
   * 训练
   */
  final static String TRAINING = "training";

  /**
   * spring: profiles: active
   */
  public static final String SPRING_PROFILES_ACTIVE_DEV = "dev";

  /**
   * 是否验证登录密码 是1
   */
  public final static Integer LOGIN_NO_CHECK_FLAG_YES = 1;

  /**
   * 是否验证登录密码 否0
   */
  public final static Integer LOGIN_NO_CHECK_FLAG_NO = 0;

  final static Long DEFAULT_USER_ID = Long.valueOf("1");

  final static String DEFAULT_USER_NAME = "系统超级管理员";
  final static String DEFAULT_DB_MYSQL = "mysql";
}
