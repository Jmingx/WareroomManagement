<%--
  Created by IntelliJ IDEA.
  User: 78289
  Date: 2020-05-06
  Time: 21:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@include file="head.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="assets/css/homeDemo.css">
    <link rel="stylesheet" href="//at.alicdn.com/t/font_1794667_lzz5nu8jh4k.css">
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="assets/js/homeAction.js"></script>
    <title>仓库管理系统</title>
</head>
<body>
<div class="whole-box">
    <div class="top-box">
        <a href="/home.jsp"><i id="logo" class="iconfont icon-cangkuguanlikufangguanli"></i></a>
        <h1 id="head">仓库管理系统</h1>
        <ul class="catalog-list">
            <li>欢迎 ${username}</li>
            <li><a href="/login?type=logout">退出</a></li>
        </ul>
    </div>

    <div class="core-box">
        <span class="business-box">
            <button id="button-business">
                <i class="iconfont icon-icon_shiwu"></i><hr>
                <h2>事务</h2>>>
            </button>
        </span>
        <span class="component-box">
            <button id="button-component">
                <i class="iconfont icon-gongyejichulingjian"></i><hr>
                <h2>零件</h2>>>
            </button>
        </span>
        <span class="supplier-box">
            <button id="button-supplier">
                <i class="iconfont icon-gongyingshang"></i><hr>
                <h2>供应商</h2>>>
            </button>
        </span>
        <div class="report-box">
            <button id="button-report">点击生成报表</button>
        </div>
    </div>
</div>
</body>
</html>