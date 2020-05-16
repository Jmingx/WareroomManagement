<%--
  Created by IntelliJ IDEA.
  User: 78289
  Date: 2020-05-08
  Time: 22:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java"%>
<%@include file="head.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="assets/css/iframe.css">
    <link rel="stylesheet" href="//at.alicdn.com/t/font_1794667_lzz5nu8jh4k.css">
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="assets/js/homeAction.js"></script>
    <meta charset="UTF-8">
    <title>report-iframe</title>
</head>
<body id="iframe-body">
<table class="main-table">
    <tr id="tr-one">
        <th id="td-one">序号</th>
        <th>库存清单编号</th>
        <th>零件编号</th>
        <th>库存量</th>
        <th>库存临界值</th>
    </tr>
    <% int i = 0;%>
    <c:forEach var="inventory" items="${inventories}">
        <tr class="tr-core">
            <td><%=++i%>
            </td>
            <td>${inventory.id}</td>
            <td>${inventory.componentId}</td>
            <td>${inventory.inventory}/个</td>
            <td>${inventory.criticalValue}/个</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>