# lan-code-api

#### 介绍
通过git拉去项目文件进行解析注释注解生成API文档

#### 特点
零侵入，无需任何配置，独立运行，注释生成文档，支持读取swagger注解，自定义注释配置，在线调试

#### 安装教程

1.  数据库配置

2.  git配置
3.  项目配置
4.  环境配置
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


#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request

#### 联系方式

qq: 3092575337 (推荐)
wx: lanyanhua1024
邮箱: lanyanhua1024@163.com
