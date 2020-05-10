<%--
  Created by IntelliJ IDEA.
  User: 78289
  Date: 2020-05-09
  Time: 12:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <link rel="stylesheet" href="//at.alicdn.com/t/font_1794667_gxvu4adbpei.css">
    <title>Register</title>
</head>
<body>
<div id="core">
    <div>
        <i class="iconfont icon-user"></i>
        <input id="name" name="name" type="text" placeholder="User Name"><br/>
    </div>
    <div>
        <i class="iconfont icon-password"></i>
        <input id="password" name="password" type="password" placeholder="Password"><br/>
    </div>
    <div>
        <i class="iconfont icon-chakan"></i>
        <input id="password-check" name="password-check" type="password" placeholder="Password-checked"><br/>
    </div>
    <div>
        <button id="submit">REGISTER>></button>
    </div>
</div>
</body>
</html>
<script>
    $("#submit").click(function () {
            if ($("#name").val() == "") {
                alert("请输入账号");
            } else if ($("#password").val() == "") {
                alert("请输入密码");
            } else if ($("#password").val() == $("#password-check").val()) {
                $.post(
                    "/register",
                    {name: $("#name").val(), password: $("#password").val()},
                    function (result) {
                        if (result != 1) {
                            alert("注册成功，你的账号为：" + $("#name").val());
                            setTimeout("window.location.href='/LOGIN.jsp'", 1000);
                        } else {
                            alert("账号:" + $("#name").val() + " 已经存在，请重新确认用户名！");
                        }
                    }
                );
            } else {
                alert("请再次检查两次输入的密码是否一致！");
            }
        }
    )
</script>
<style>
    body{
        position: relative;
        background: url("assets/images/5.jpg");
        display: flex;
    }

    #core{
        margin-top: 10%;
        float: left;
        margin-left: 37%;
        position: relative;
        width: 400px;
        height: 350px;
        background-color: rgba(194, 159, 169, 0.38);
        text-align: center;
        padding: 50px;
        border-radius: 20px;
    }

    #core div{
        margin-top: 40px;
    }

    input{
        border-radius: 10px;
        width: 300px;
        text-align: center;
        height: 40px;
        font-size: 20px;
        outline: none;
        border-color: #fb151e;
        background-color: rgba(0, 0, 0, 0.46);
        color: #ffff;

    }

    button{
        border-radius: 10px;
        width: 150px;
        text-align: center;
        height: 40px;
        font-size: 20px;
        outline: none;
        border-color: #fb151e;
        background-color: rgba(213, 251, 152, 0);
        color: #ffff;
        cursor: pointer;
        transition-duration: 0.7s;
    }

    button:hover {
        background-color: #4CAF50; /* Green */
        color: white;
        box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);
    }

    i{
        color: #ffff;
    }
</style>