# gorgeous-doc

#### 介绍
直接上翻译 gorgeous：非常漂亮的;美丽动人的;令人愉快的;绚丽的;灿烂的;华丽的

通过git拉去项目文件进行解析注释注解生成API文档  
零侵入，无需任何配置，独立运行，注释生成文档，支持读取swagger注解，自定义注释配置，在线调试  

切换： 
项目已引用swagger注解包，可以读取swagger注释  
支持自定义注释读取，使用特定注释读取项目也可以无损替换  

#### 安装教程

1.  数据库配置

application.yml
```
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/lan-code-api?useSSL=true&useUnicode=true&characterEncoding=utf8&allowMultiQueries=true
    username: root
    password: AAaa123456
```
[database.sql](https://gitee.com/lanyanhua/lan-code-api/blob/master/database.sql)  
启动项目访问地址 http://localhost:5160/lApi/docs
![初始化页面](https://images.gitee.com/uploads/images/2020/1222/125336_1ce8bcfd_1793098.png "屏幕截图.png")  

2.  git配置

git数据在库里只会有一条数据。现在没有开发用户项目组方面的，我给这个项目的定位是一个项目组一个API项目互不干涉  

字段  
本地仓库地址：拉取代码存放的地址。docker发布的朋友记得将这个文件夹共享到宿主机  
用户名：git用户名 拉取代码时需要地址在项目上配置  
密码：git用户密码  

3.  项目配置

字段  
项目名：项目名  
git地址：git地址  
分支名称：分支  
上下文路径：配置 server.servlet.context-path 值  
端口：端口  

上下文路径、端口这两个配置是配合环境配置使用  
单体项目直接在环境中配置域名就好了  
微服务架构  
本地 通过端口和项目名访问的  http:localhost:8080/user  
网关 只需要项目名就可以了  http:test.lan.com/user  

4.  环境配置

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
dev开发环境 域名：http://{{开发网管地址}} 使用项目路径  
test开发环境 同上  


5.  注释配置


#### 使用说明

1.  API页面
2.  搜索
3.  切换
4.  更新
5.  设置

#### 注释规范

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


6.  跨域问题  

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



#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request

#### 联系方式

qq: 3092575337 (推荐)
wx: lanyanhua1024
邮箱: lanyanhua1024@163.com
