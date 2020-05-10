<%--
  Created by IntelliJ IDEA.
  User: 78289
  Date: 2020-05-06
  Time: 23:45
  To change this template use File | Settings | File Templates.
--%>
<%--head头部文件用来判断是否登录，因为每个页面都需要判断一次，include可以方便管理--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    //要求登录了才能进入这个界面
    if (session.getAttribute("username")==null)
        response.sendRedirect("/LOGIN.jsp");
%>