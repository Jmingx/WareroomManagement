<%--
  Created by IntelliJ IDEA.
  User: 78289
  Date: 2020-05-25
  Time: 5:39
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: 78289
  Date: 2020-05-25
  Time: 2:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>供应商详细信息</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/static/css/oksub.css">
    <script type="text/javascript" src="/static/lib/loading/okLoading.js"></script>
</head>
<body>
<div class="ok-body">
    <form class="layui-form ok-form" lay-filter="openApply" >

        <fieldset class="layui-elem-field">
            <legend>供应商详情</legend>
            <div class="layui-field-box">
                <div class="layui-form-item">
                    <label class="layui-form-label">ID</label>
                    <div class="layui-input-block">
                        <input type="text" name="id" disabled placeholder="请输入ID" autocomplete="off" class="layui-input"
                               lay-verify="required">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">供应商名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="name" disabled placeholder="请输入供应商名称" autocomplete="off" class="layui-input" lay-verify="required">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">联系方式</label>
                    <div class="layui-input-block">
                        <input type="text" name="contact" disabled placeholder="请输入联系方式" autocomplete="off" class="layui-input" lay-verify="required">
                    </div>
                </div>
            </div>
        </fieldset>
    </form>
</div>
<script src="/static/lib/layui/layui.js"></script>
<script type="text/javascript">

    let initData;

    function initForm(data) {
        let jsonString = JSON.stringify(data);
        initData = JSON.parse(jsonString);
    }

    layui.use(["element","jquery", "form", "laydate", "okLayer", "okUtils"], function () {
        let form = layui.form;
        let laydate = layui.laydate;
        let okLayer = layui.okLayer;
        let okUtils = layui.okUtils;
        let $ = layui.jquery;

        okLoading.close($);
        //
        // laydate.render({elem: "#sendDate", type: "date",trigger: 'click'});
        // laydate.render({elem:'#checkDate',type:"date",trigger: 'click'});

        form.val("openApply",initData);

    });
</script>
</body>
</html>
