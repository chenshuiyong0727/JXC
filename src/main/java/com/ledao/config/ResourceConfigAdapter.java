package com.ledao.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 创建时间：2019年2月15日 下午1:52:49 项目名称：server-provider 类说明：将物理磁盘文件存放的绝对路径映射为一个虚拟路径，就可以通过SpringBoot服务来访问文件了
 *
 * @author guobinhui
 * @since JDK 1.8.0_51
 */
@Configuration
public class ResourceConfigAdapter extends WebMvcConfigurerAdapter {

  @Value("${file-save-path}")
  private String fileSavePath;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/images/**").addResourceLocations("file:" + fileSavePath);
    System.out.println("file:"+fileSavePath);
  }
}
