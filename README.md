# gorgeous-doc

#### 介绍
直接上翻译 gorgeous：非常漂亮的;美丽动人的;令人愉快的;绚丽的;灿烂的;华丽的

通过git拉去项目文件进行解析注释注解生成API文档  
零侵入，无需任何配置，独立运行，注释生成文档，支持读取swagger注解，自定义注释配置，在线调试  

无损替换： 
项目已引用swagger注解包，可以读取swagger注释  
支持自定义注释读取，使用特定注释读取项目也可以无损替换  

### 安装教程

启动地址：域名:[端口]/[上下文]/docs  
例子：http://localhost:5160/gorgeous/docs  
端口、上下文可在配置文件中修改  
默认端口：5160  
默认上下文：/gorgeous 


#### 发布版

[https://gitee.com/lanyanhua/gorgeous-doc/releases ](http://)

#### docker发布
这个我后面在弄一下，这次改了git的拉去方式

### 使用说明

#### 项目配置

字段  
项目名：项目名  
git地址：git地址  
分支名称：分支  
上下文路径：配置文件 server.servlet.context-path 的值  
端口：端口  

上下文路径、端口这两个配置是配合环境配置使用  
单体项目直接在环境中配置域名就好了  
微服务架构  
本地 通过端口和项目名访问的  http:localhost:8080/user  
网关 只需要项目名就可以了  http:test.lan.com/user  

#### 环境配置

字段  
名称：环境名称  
域名：环境域名  
headerKey：ajax请求携带的header值  
是否使用项目端口：  
是否使用项目上下文路径：  

单体项目：项目里面的路径和端口和环境里面是否使用端口路径都可以不用管  

微服务架构  
建议环境配置 local、dev、test  
local本地开发 域名：http://localhost 使用项目端口 使用项目路径  
dev开发环境 域名：http://{{开发网关地址}} 使用项目路径  
test开发环境 同上  


#### 注释配置

支持注释自定义配置，项目对注释规范这块要求其实不是很高。
默认的注释读取  

```
class
/**
 * {{类名称}}
 * @author: lanyanhua
 * @date: 2020/12/4 8:38 下午
 */
public class ProjectController {
}

方法
/**
 * {{方法名称}}
 * @param {{参数名称}} {{参数描述}}
 * @return {{返回值描述}}
 */
public BaseResponse&lt;Integer&gt; addProject(@RequestBody ProjectAddVo vo) {
}

字段
/**
 * {{字段描述}}
 */
private String name;
```


### 界面说明

1.  API页面
2.  搜索
3.  切换
4.  更新
5.  设置


### 跨域问题


后端项目添加一下配置


```
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 解决跨域 
 */
@Configuration
public class AdditionalWebConfig {

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
```




#### 联系方式

qq: 3092575337 (推荐)
wx: lanyanhua1024
邮箱: lanyanhua1024@163.com
