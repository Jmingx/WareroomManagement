<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" class="page-fill">
<head>
    <meta charset="UTF-8">
    <title>仓库管理系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/static/css/oksub.css"/>
    <style type="text/css">

    </style>
</head>
<body class="page-fill">
<div class="page-fill" id="login">
    <form class="layui-form">
        <div class="login_face">
            <img src="/static/images/logo1.jpg">

        </div>
        <h2 style="text-align: center;margin-bottom: 10px">仓库管理系统</h2>
        <div class="layui-form-item input-item">
            <label for="username">用户名</label>
            <input type="text" lay-verify="required" name="name" placeholder="请输入账号" autocomplete="off" id="username"
                   class="layui-input">
        </div>
        <div class="layui-form-item input-item">
            <label for="password">密码</label>
            <input type="password" lay-verify="required|password" name="password" placeholder="请输入密码" autocomplete="off"
                   id="password" class="layui-input">
        </div>
<%--        <div class="layui-form-item input-item captcha-box">--%>
<%--            <label for="captcha">验证码</label>--%>
<%--            <input type="text" lay-verify="required|captcha" name="captcha" placeholder="请输入验证码" autocomplete="off"--%>
<%--                   id="captcha" maxlength="4" class="layui-input">--%>
<%--            <div class="img">--%>
<%--                <img src="checkCode" onclick="this.src=this.src+'?d='+Math.random();"--%>
<%--                     title="点击刷新">--%>
<%--            </div>--%>
<%--        </div>--%>
        <div class="layui-form-item">
            <button class="layui-btn layui-block" lay-filter="login" lay-submit="">登录</button>
        </div>
    </form>
</div>
<!--js逻辑-->
<script src="/static/lib/layui/layui.js"></script>
<script>
    layui.use(["form", "okGVerify", "okUtils", "okLayer"], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var okGVerify = layui.okGVerify;
        var okUtils = layui.okUtils;
        var okLayer = layui.okLayer;


        // /**
        //  * 数据校验
        //  */
        form.verify({
            password: [/^[\S]{6,12}$/, "密码必须6到12位，且不能出现空格"],

        });

        /**
         * 表单提交
         */
        form.on("submit(login)", function (data) {
            okUtils.ajax("/login", "post", data.field, true).done(function (response) {
                //这里的message原先为msg，但后台返回message，所以特效报错了

                if (response.message === "fail"){
                    layer.msg(response.data, {icon: 2, time: 1000}, function () {
                        // 刷新当前页table数据
                        $(".layui-laypage-btn")[0].click();
                    });
                }else {
                    okLayer.greenTickMsg(response.message, function () {
                        window.location = "../index.jsp";
                    })
                }




            }).fail(function (error) {
                console.log(error)
            });
            return false;
        });

        /**
         * 表单input组件单击时
         */
        $("#login .input-item .layui-input").click(function (e) {
            e.stopPropagation();
            $(this).addClass("layui-input-focus").find(".layui-input").focus();
        });

        /**
         * 表单input组件获取焦点时
         */
        $("#login .layui-form-item .layui-input").focus(function () {
            $(this).parent().addClass("layui-input-focus");
        });

        /**
         * 表单input组件失去焦点时
         */
        $("#login .layui-form-item .layui-input").blur(function () {
            $(this).parent().removeClass("layui-input-focus");
            if ($(this).val() != "") {
                $(this).parent().addClass("layui-input-active");
            } else {
                $(this).parent().removeClass("layui-input-active");
            }
        })
    });
</script>
</body>
</html>
