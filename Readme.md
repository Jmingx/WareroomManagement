前端使用原生jquery+html+css+js
后台使用经典servlet+jsp

基本上没用什么框架，非常简单的练手作业

###### 记录一下开发日记
###### 我人傻了，现在才想起写日志

## 2020-5-9
1.完成各个模块的代码（零件+供应商+生成报表）
2.忘了

## 2020-5-10
1.完成项目部署（搭建好remote环境，随时可以上传）

2.解决了**中文乱码**问题
工具类
```java
    public static String transcoding(String source){
        try {
            byte[] dates =source.getBytes("ISO-8859-1");
            source=new String(dates,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return source;
    }
```
拦截器
```java
public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Map map = request.getParameterMap();
        Set<String> set = map.keySet();
        String source ;
        for (String string : set){
            source = Decoding.transcoding(servletRequest.getParameter(string));
            servletRequest.setAttribute(string,source);
        }
        filterChain.doFilter(request, response);
    }
```
一直都没有留意到**中文乱码**问题，通过翻博客，试了修改tomcat的server.xml、jsp添加表头字符限制、res reps设置字符集、数据库改字符集，最后才发现是tomcat源码的问题，**tomcat默认使用"ISO-8859-1"，不是utf-8**

思路，把request拦截了，然后获取parameter的map，通过遍历，把parameter的键值对，转码后存进request的attribute。但是，这样做有一个缺点，就是：
①.需要将每一个servlet的getParameter转换成getAttribute。
②.还要区分数字类（非中文不需要转换）。

3.输入数据增加了限制（类型限制，非空限制）

#### PS 今天光是debug就大半天了，而且界面确实太丑了，用了>Filter，真香！
#### PS 确实是挺懵的，一路开发走来，测试全是用英文，导致突然冒了个**中文乱码**

#### 部署到服务器上又出问题了，又查了下资料，原来tomcat默认字符集已经换成utf-8了，那就把tomcat的配置改改吧，成功了
**在tomcat server.xml的Connector标签加上useBodyEncodingForURI="true" URIEncoding="UTF-8"**

## 2020-5-13
1.增加了**修改**功能
2.修复了部分bug，密码登录失败将会有反馈

## 2020-5-16
1.利用回调函数修复了**延迟**，取消了利用延迟才能加载成功的奇怪实现
2.增加了显示库存清单功能
3.修改了增加、删除、修改事务时库存清单数据错误bug

待改进：后台没有利用filter实现**用户在线**功能，有空修复一下