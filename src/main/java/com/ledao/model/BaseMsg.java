package com.ledao.model;

/**
 * 总消息类 用来存放消息 opsli将消息全部提取出至一个总文件
 *
 * @author Parker
 * @date 2020-09-22 17:07
 */
public interface BaseMsg {

  /**
   * 获取消息的状态码
   */
  Integer getCode();

  /**
   * 获取消息提示信息
   */
  String getMessage();

}
