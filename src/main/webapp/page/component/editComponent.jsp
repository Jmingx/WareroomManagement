<%--
  Created by IntelliJ IDEA.
  User: 78289
  Date: 2020-05-25
  Time: 2:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>修改零件信息</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/static/css/oksub.css">
    <script type="text/javascript" src="/static/lib/loading/okLoading.js"></script>
</head>
<body>
<div class="ok-body">
    <form class="layui-form ok-form" lay-filter="editApply" >
        <div class="layui-form-item">
            <label class="layui-form-label">ID</label>
            <div class="layui-input-block">
                <input type="text" name="id" disabled placeholder="请输入ID" autocomplete="off" class="layui-input"
                       lay-verify="required">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">供应商编号</label>
            <div class="layui-input-block">
                <input type="text" name="supplierId" disabled placeholder="请输入供应商编号" autocomplete="off" class="layui-input" lay-verify="required|number">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">目前价格</label>
            <div class="layui-input-block">
                <input type="text" name="price" placeholder="请输入目前价格" autocomplete="off" class="layui-input" lay-verify="required|number">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">零件名称</label>
            <div class="layui-input-block">
                <input type="text" name="name" placeholder="请输入零件名称" autocomplete="off" class="layui-input"
                       lay-verify="required">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="edit">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
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

        laydate.render({elem: "#sendDate", type: "date",trigger: 'click'});

        form.val("editApply",initData);

        form.on("submit(edit)", function (data){

            okUtils.ajax("/component?type=update", "get", data.field, true).done(function (response) {

                if (response.message === "fail"){

                    layer.msg(response.message+" 售价不能小于或等于0", {icon: 2, time: 3000}, function () {
                        // 刷新当前页table数据
                        $(".layui-laypage-btn")[0].click();
                    });

                }else{
                    okLayer.greenTickMsg(response.message, function () {
                        parent.layer.close(parent.layer.getFrameIndex(window.name));
                    });
                }
            }).fail(function (error) {
                console.log(error)
            });
            return false;
        });
    });
</script>
</body>
</html>
