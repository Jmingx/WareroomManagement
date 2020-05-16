<%--
  Created by IntelliJ IDEA.
  User: 78289
  Date: 2020-05-08
  Time: 8:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@include file="head.jsp" %>
<html>
<head>
    <link rel="stylesheet" href="assets/css/iframe.css">
    <link rel="stylesheet" href="//at.alicdn.com/t/font_1794667_lzz5nu8jh4k.css">
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="assets/js/homeAction.js"></script>
    <meta charset="UTF-8">
    <title>business-iframe</title>
</head>
<body id="iframe-body">
<table class="main-table">
    <tr id="tr-one">
        <th id="td-one">序号</th>
        <th>零件名称</th>
        <th>零件编号</th>
        <th>事务</th>
    </tr>
    <% int i = 0; %>
    <c:forEach var="business" items="${businesses}">
        <tr class="tr-core">
            <td><%=++i%>
            </td>
                <%--            注意这里的map取值--%>
            <td>${map[business]}</td>
            <td>${business.componentId}</td>
            <td>${business.business}/个</td>
            <td>
                <button id="delete" style="font-size: 15px;border-radius: 20px;outline: none;background-color: #c2c2c2"
                        onclick="delete_business(${business.id},${business.business})">删除
                </button>
                <button id="update" style="font-size: 15px;border-radius: 20px;outline: none;background-color: #c2c2c2"
                        onclick="update_business(${business.id},${business.componentId},${business.business})">更新
                </button>
            </td>
        </tr>
    </c:forEach>
</table>

<button class="add-button" style="font-size: 15px;border-radius: 20px;outline: none;background-color: #c2c2c2">增加</button>
<div class="input-div" align="center" hidden>
    <select id="input-componentId" type="text" name="componentId" style="border-radius: 20px;outline: none">
        <option>-请选择零件-</option>
        <c:forEach var="component" items="${components}">
            <option>${component.name}</option>
        </c:forEach>
    </select>
    <input id="input-business" type="text" name="business" placeholder="输入事务数量(例如：+10，-10)"
           style="width: 200px;text-align: center;border-radius: 10px;outline: none;margin-top: 10px"><br/>
    <button id="add-submit"
            style="font-size: 15px;border-radius: 20px;outline: none;background-color: #c2c2c2;margin-top: 10px">提交
    </button>
</div>
</body>
</html>
<script>
    // 弹出增加按钮
    $(function () {
        $(".add-button").click(function () {
            $(".input-div").show(1000);
        })
    })

    $(function () {
        $("#add-submit").click(function () {
            //AJAX传参
            var num = $("#input-business").val();
            if ($("#input-componentId").val() != "-请选择零件-" &&
                num != 0 &&
                !isNaN(num)) {
                $.get("/business",
                    {
                    type: "insert",
                    componentId: $("#input-componentId").val(),
                    "business": $("#input-business").val()
                    },
                    function (msg) {
                        if (msg=="1"){
                            alert("增加成功！");
                            //需要设置定时器，等数据更新完再刷新
                            parent.location.reload();
                        }
                        else {
                            alert("增加失败！")
                        }
                    });
            } else {
                alert("输入数据有误！");
            }
        })
    })

    function delete_business(id,business) {
        if (confirm("确认删除?")) {
            $.get(
                "/business", {type: "delete", id: id,business:business},
                function (msg) {
                    if (msg=="1"){
                        alert("删除成功！");
                        parent.location.reload();
                    }else {
                        alert("删除失败！");
                    }
                }
            );
        }
    }

    function update_business(id, componentId,oldBusiness) {
        if (confirm("是否更新？")) {
            var business = prompt("请输入更新后的事务数量？").trim();
            if (isNaN(business)) {
                alert("事务只能为数值！");
                return;
            }
            if (confirm("是否确认更新？")) {
                $.get(
                    "/business",
                    {
                        type: "update",
                        id: id,
                        business: business,
                        componentId: componentId,
                        oldBusiness:oldBusiness
                    }
                    ,
                    function (msg) {
                        if (msg == "1"){
                            alert("更新成功！");
                            parent.location.reload();
                        }else {
                            alert("更新失败！");
                        }
                    }
                )
            }
        }
    }
</script>
