<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>LOGIN</title>
    <link rel="stylesheet" type="text/css" href="assets/css/NewLogin.css">
    <link rel="stylesheet" href="//at.alicdn.com/t/font_1794667_gxvu4adbpei.css">
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
</head>
<body>
<%
    //防止重复登录
    if (session.getAttribute("username")!=null)
        response.sendRedirect("/home.jsp");
%>
<div id="login_box">
    <h2>LOGIN</h2>

    <div id="form">
            <div id="input_box">
                <i class="iconfont icon-user"></i>
                <input id="text" type="text" placeholder="Username" name="name">
            </div>
            <div id="input_box">
                <i class="iconfont icon-password"></i>
                <input id="password" type="password" placeholder="Password" name="password">
            </div>
            <button id="button">Sign in</button>
    </div>
    <!--        在这里跳转到修改密码、申述页面-->

    <br/>
    <div id="forget">
        <a href="/register.jsp">忘记密码</a>
    </div>

    <!--        在这里跳转到注册页面-->
    <div id="Signup">
        <a href="/register.jsp">注册账号</a>
    </div>
</div>
</body>
</html>
<script>
    $(document).ready(function() {
        $("#button").click(function () {
            if (!$("#text").val()){
                alert("请输入用户名！");
                return;
            }else if (!$("#password").val()){
                alert("密码不能为空！");
                return;
            }else {
                $.post(
                    "/login",
                    {
                        name:$("#text").val(),
                        password:$("#password").val(),
                    },
                    function (msg) {
                        if (msg=="1"){
                            window.location.href="home.jsp";
                        }else {
                            alert("账户不存在或者密码错误！");
                        }
                    }
                )
            }
        })
    });
</script>