package com.ledao.util;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * http util to send data
 *
 * @author xuxueli
 * @version 2015-11-28 15:30:59
 */
public class HttpClientUtil {

  /*
  同步到小程序平台
 */
  public static final String MINI_SYNC_CHANNEL = "https://www.huohao8.com/s?kw=";

  /*
  同步到小程序平台
 */
  public static final String MINI_SYNC_CHANNEL_LABEL = "/admin-api/ssoApi/act/syncChannelLabel";

  /*
  同步到小程序平台
 */
  public static final String MINI_SYNC_QR_CODE = "/admin-api/ssoApi/act/syncQrcode";

  /*
  获取小程序码地址
 */
  public static final String GET_QR_CODE_URL = "/api/util/getMaQRCodeUrl";
  /*
  获取仅助力人数
 */
  public static final String GET_ONLY_HELP_COUNT = "/api/applet/activity/queryOnlyHelpCount";

  /*
同步到小程序平台
*/
  public static final String UPDATEUSERREDENVELOPESTATUS = "/applet/activity/updateUserRedEnvelopeStatus";

  /**
   * 部门用户树形列表 路径
   */
  public static final String LIST_DW_USERS = "/v1/syncData/listDropDownUsers";

  /**
   * 通过sessionId退出登录 路径
   */
  public static final String LOGOUT_BY_SESSIONID = "/v1/sso/logoutBySessionId";

  /**
   * 通过key得到权限列表 路径
   */
  public static final String GET_FUNCTIONS_BY_KEY = "/v1/sso/getFunctions";

  /**
   * 通过sessionId得到用户信息 路径
   */
  public static final String GET_USER_BY_SESSIONID = "/v1/sso/getUserBySessionId";
  /**
   * 同步数据接口类型 - 新增
   */
  public static final Integer SYNC_OP_TYPE_ADD = 1;
  /**
   * 同步数据接口类型 - 修改
   */
  public static final Integer SYNC_OP_TYPE_UPDATE = 2;
  /**
   * 同步数据接口类型 - 删除
   */
  public static final Integer SYNC_OP_TYPE_DELETE = 3;

  /**
   * 二维码宽度
   */
  public static final Integer QR_CODE_WIDTH = 80;


  /**
   * post请求
   */
  public static String post(String url, String json, Map<String, String> headers) {
//    log.info("====url ========== " + url);
//    log.info("====json ========== " + json);
    HttpPost httpPost = null;
    CloseableHttpClient httpClient = null;
    try {
      httpPost = new HttpPost(url);
      if (json != null) {
        // 构造一个请求实体
        StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(stringEntity);
      }
      RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000)
          .setConnectTimeout(30000).build();
      httpPost.setConfig(requestConfig);
      // headers
      if (headers != null && headers.size() > 0) {
        for (Map.Entry<String, String> headerItem : headers.entrySet()) {
          httpPost.setHeader(headerItem.getKey(), headerItem.getValue());
        }
      }
      httpClient = HttpClients.custom().disableAutomaticRetries().build();
      // parse response
      HttpResponse response = httpClient.execute(httpPost);
      HttpEntity entity = response.getEntity();
      if (null != entity) {
        if (response.getStatusLine().getStatusCode() == 200) {
          String responseMsg = EntityUtils.toString(entity, "UTF-8");
          EntityUtils.consume(entity);
          return responseMsg;
        }
        EntityUtils.consume(entity);
      }
      System.out
          .println("http statusCode error, statusCode:" + response.getStatusLine().getStatusCode());
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return e.getMessage();
    } finally {
      if (httpPost != null) {
        httpPost.releaseConnection();
      }
      if (httpClient != null) {
        try {
          httpClient.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * 发送get请求
   *
   * @param url 请求URL
   * @param param 请求参数 key:value url携带参数 或者无参可不填
   */
  public static String doGet(String url, Map<String, String> param) {

    // 创建Httpclient对象
    CloseableHttpClient httpclient = HttpClients.createDefault();

    String resultString = "";
    CloseableHttpResponse response = null;
    try {
      // 创建uri
      URIBuilder builder = new URIBuilder(url);
      if (param != null) {
        for (String key : param.keySet()) {
          builder.addParameter(key, param.get(key));
        }
      }
      URI uri = builder.build();
      // 创建http GET请求
      HttpGet httpGet = new HttpGet(uri);
// 执行请求
      response = httpclient.execute(httpGet);
      // 判断返回状态是否为200
      if (response.getStatusLine().getStatusCode() == 200) {
        resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (response != null) {
          response.close();
        }
        httpclient.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return resultString;
  }

  public static String getData(String model) {
    String url = MINI_SYNC_CHANNEL + model;
    String s = HttpClientUtil.doGet(url, null);
    s = s.replaceAll(" ", "");
    s = s.replaceAll("\n", "");
    s = s.replaceAll("\r", "");
    if (s.contains("搜索到<spanclass=\"c-red\">0</span>个")){
      System.out.println("未找到");
      return null;
    }
    s = s.substring(s.indexOf("item-list-pic"), s.indexOf("item-list-pic") + 300);
    s = s.substring(s.indexOf("imgsrc=") + 8, s.indexOf("\"/></a></div>"));
    System.out.println(s);
    return s;
  }


  public static void main(String[] args) throws IOException {
//    Map<String, String> headers = reqHeader("1002", "6df8665d37ec9d01213d34623ddx8913");
//    String url = "http://192.168.8.82:28087/gw/op/v1/sso/getUserBySessionId";
//    Map<String, Object> param = new HashMap<>();
//    param.put("sessionId", "a891a8b3-b1e4-4085-9c89-06f94877d30f_cacba3a1c871485ca2950122502c2ba2");
//    ResponseMsg object = HttpClientUtil.getMsgByPostByMap(url, param, headers);
//    log.info("s ", object);
    String url = "https://www.huohao8.com/s?kw=DH5927-0063";
    String s = HttpClientUtil.doGet(url, null);
    System.out.println(s);
    s = s.replaceAll(" ", "");
    //  System.out.println(s.length());
    s = s.replaceAll("\n", "");
    s = s.replaceAll("\r", "");
    if (s.contains("搜索到<spanclass=\"c-red\">0</span>个")){
      System.out.println("未找到");
      return;
    }
    // System.out.println(s.length());
    //  System.out.println(s);
    s = s.substring(s.indexOf("item-list-pic"), s.indexOf("item-list-pic") + 300);
    System.out.println(s);
    s = s.substring(s.indexOf("imgsrc=") + 8, s.indexOf("\"/></a></div>"));
    System.out.println(s);
  }


}
