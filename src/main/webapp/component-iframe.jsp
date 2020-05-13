<%--
  Created by IntelliJ IDEA.
  User: 78289
  Date: 2020-05-08
  Time: 18:35
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
        <th>零件编号</th>
        <th>零件名称</th>
        <th>价格</th>
        <th>供应商编号</th>
    </tr>
    <% int i = 0; %>
    <c:forEach var="component" items="${components}">
        <tr class="tr-core">
            <td><%=++i%></td>
                <%--注意这里的map取值--%>
            <td>${component.id}</td>
            <td>${component.name}</td>
            <td>${component.price}￥</td>
            <td>${component.supplierId}</td>
            <td>
                <button id="delete" style="font-size: 15px;border-radius: 20px;outline: none;background-color: #c2c2c2"
                        onclick="delete_component(${component.id},${component.supplierId})">删除
                </button>
                <button id="update" onclick="update_business(${component.id},${component.supplierId})" style="font-size: 15px;border-radius: 20px;outline: none;background-color: #c2c2c2" >更新</button>
            </td>
        </tr>
    </c:forEach>
</table>

<button class="add-button" style="font-size: 15px;border-radius: 20px;outline: none;background-color: #c2c2c2">增加
</button>
<div class="input-div" align="center" hidden>
    <input id="input-name" type="text" name="name" placeholder="输入零件名称"
           style="width: 100px;text-align: center;border-radius: 10px;outline: none">

    <input id="input-price" type="text" name="price" placeholder="输入价格(例如：10)"
           style="width: 200px;text-align: center;border-radius: 10px;outline: none;margin-top: 10px"><br/>
    <%--    <input id="input-supplierId" type="text" name="supplierId" placeholder="供应商编号"--%>
    <%--           style="width: 200px;text-align: center;border-radius: 10px;outline: none;margin-top: 10px">--%>
    <select id="input-supplierId" type="text" name="supplierId"
            style="border-radius: 20px;outline: none;margin-top: 10px">
        <option>-请选择供应商-</option>
        <c:forEach var="supplier" items="${suppliers}">
            <option>${supplier.name}</option>
        </c:forEach>
    </select><br/>
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
            if ($("#input-name").val()!=null&&$("#input-price").val()>0&&($("#input-price").val()instanceof Number)&&$("#input-supplierId").val()!== "-请选择供应商-") {
                //AJAX传参
                $.get("/component", {
                    type: "insert",
                    name: $("#input-name").val(),
                    price: $("#input-price").val(),
                    supplierId: $("#input-supplierId").val()
                });
                //需要设置定时器，等数据更新完再刷新
                window.setTimeout('window.location.reload()', 500);
            }else {
                alert("输入数据有误！");
            }
        })
    })

    function delete_component(id) {
        if (confirm("确认删除?")){
            $.get("/component", {type: "delete", id: id});
            // $(obj).parents("tr").remove();
            window.setTimeout('window.location.reload()', 500);
        }
    }

    function update_business(id,supplierId) {
        if (confirm("是否更新？")){
            var componentName = prompt("请输入更新后的零件名称？").trim();
            if (componentName==""){
                alert("零件名称不能为空值");
                return;
            }
            var price = prompt("请输入更新后的价格").trim();
            var price = new Number(price);
            if (!(price > 0) ){
                alert("请输入正确的数值!"+"          "+price);
                return;
            }
            supplierId = new Number(supplierId);
            if (confirm("是否确认更新？"+componentName+":"+price+"￥")){

                $.get(
                    "/component",
                    {
                        type:"update",
                        componentId:id,
                        componentName:componentName,
                        price:price,
                        supplierId:supplierId
                    }
                    ,
                    function (msg) {
                            alert("更新成功！");
                            window.setTimeout('window.location.reload()', 500);
                    }
                )
            }
        }
    }
</script>