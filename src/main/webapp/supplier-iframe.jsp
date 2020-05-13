<%--
  Created by IntelliJ IDEA.
  User: 78289
  Date: 2020-05-08
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
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
        <th>供应商编号</th>
        <th>供应商名称</th>
        <th>联系方式</th>
    </tr>
    <% int i = 0; %>
    <c:forEach var="supplier" items="${suppliers}">
        <tr class="tr-core">
            <td><%=++i%>
            </td>
                <%--            注意这里的map取值--%>
            <td>${supplier.id}</td>
            <td>${supplier.name}</td>
            <td>${supplier.contact}</td>
            <td>
                <button id="delete" style="font-size: 15px;border-radius: 20px;outline: none;background-color: #c2c2c2"
                        onclick="delete_business(${supplier.id},this)">删除
                </button>
                <button id="update" style="font-size: 15px;border-radius: 20px;outline: none;background-color: #c2c2c2" onclick="update_business(${supplier.id})">更新</button>
            </td>
        </tr>
    </c:forEach>
</table>

<button class="add-button" style="font-size: 15px;border-radius: 20px;outline: none;background-color: #c2c2c2">增加
</button>
<div class="input-div" align="center" hidden>
    <input id="input-name" type="text" name="name" placeholder="输入供应商名称"
           style="width: 100px;text-align: center;border-radius: 10px;outline: none">
    <input id="input-contact" type="text" name="contact" placeholder="请输入联系方式"
           style="width: 200px;text-align: center;border-radius: 10px;outline: none;margin-top: 10px"><br/>
    <button id="add-submit"
            style="font-size: 15px;border-radius: 20px;outline: none;background-color: #c2c2c2;margin-top: 10px">提交
    </button>
</div>
</body>
</html>
<script>
    $(function () {
        $(".add-button").click(function () {
            $(".input-div").show(1000);
        })
    })

    $(function () {
        $("#add-submit").click(function () {
            if ($("#input-name").val() != "" && $("#input-contact").val() != "") {
                //AJAX传参
                $.get("/supplier", {
                    type: "insert",
                    name: $("#input-name").val(),
                    contact: $("#input-contact").val()
                });
                //需要设置定时器，等数据更新完再刷新
                window.setTimeout('window.location.reload()', 500);
            } else {
                alert("请检查输入是否符合格式！");
            }
        })

    })

    function delete_business(id, obj) {
        if (confirm("确认删除?")){
            $.get("/supplier", {type: "delete", id: id});
            // $(obj).parents("tr").remove();
            window.setTimeout('window.location.reload()', 500);
        }
    }

    function update_business(id) {
        if (confirm("是否更新？")){
            var name = prompt("请输入更新后的供应商名称：").trim();
            if (name == ""){
                alert("供应商名称不能为空！");
                return;
            }
            var contact = prompt("请输入更新后的供应商联系方式：").trim();
            if (confirm("是否确认更新？")){
                $.get(
                    "/supplier",
                    {
                        type:"update",
                        name:name,
                        contact:contact,
                        id:id,
                    }
                    ,
                    function () {
                        alert("更新成功！");
                        window.setTimeout('window.location.reload()', 500);
                    }
                )
            }
        }
    }
</script>
