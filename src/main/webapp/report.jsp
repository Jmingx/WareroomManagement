<%--
  Created by IntelliJ IDEA.
  User: 78289
  Date: 2020-05-07
  Time: 0:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@include file="head.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="assets/css/homeDemo.css">
    <link rel="stylesheet" href="//at.alicdn.com/t/font_1794667_lzz5nu8jh4k.css">
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="assets/js/homeAction.js"></script>
    <meta charset="UTF-8">
    <title>报表</title>
</head>
<body>
<div class="whole-box">
    <div class="top-box">
        <a href="/home.jsp"><i id="logo" class="iconfont icon-cangkuguanlikufangguanli"></i></a>
        <h1 id="head">仓库管理系统-报表</h1>
        <ul class="catalog-list">
            <li>欢迎 ${username}</li>
            <li><a href="/login?type=logout">退出</a></li>
        </ul>
    </div>
    <div class="business-mian-layer">
        <h1>报表生成完成!</h1>
        <div class="business-mian-layer" style="height: 500px;width: 80%;border-radius: 20px">
            <div class="table-div">
                <iframe src="report-iframe.jsp" class="iframe" width="100%" height="500px" style="border-radius: 20px"></iframe>
            </div>
        </div>
    </div>
</div>
</body>
</html>
