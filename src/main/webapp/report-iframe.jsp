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
        <th>需要采购零件名称</th>
        <th>需要采购数量</th>
        <th>零件编号</th>
        <th>可选供应商</th>
        <th>供应商联系方式</th>
        <th>库存临界值</th>
        <th>单价</th>
        <th>总价</th>
    </tr>
    <% int i = 0 , total = 0;%>
    <c:forEach var="report" items="${reportList}">
        <tr class="tr-core">
            <td><%=++i%>
            </td>
                <%--            注意这里的map取值--%>
            <td>${report.componentName}</td>
            <td>${report.amount}/个</td>
            <td>${report.componentId}</td>
            <td>${report.supplierName}</td>
            <td>${report.supplierContact}</td>
            <td>100个</td>
            <td>${report.price}￥</td>
            <td>${report.total}￥</td>
        </tr>
    </c:forEach>
    <tr class="tr-core">
        <td colspan="5" style="color: #fdf71e">总价  : </td>
        <td style="color: #fdf71e">${summary}￥</td>
    </tr>
</table>

</body>
</html>