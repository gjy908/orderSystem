<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登录 - 点餐系统</title>
    <!-- layui样式 -->
    <link rel="stylesheet" href="https://cdn.staticfile.org/layui/2.5.7/css/layui.css">
    <!-- jQuery CDN -->
    <script src="https://cdn.staticfile.org/jquery/3.6.0/jquery.min.js"></script>
    <!-- layui.js -->
    <script src="https://cdn.staticfile.org/layui/2.5.7/layui.js"></script>
    <style>
        body, html {
            height: 100%;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            background: url('http://img.yipic.cn/thumb/55fb3262/cb79524d/f41ee8ff/a33b0cc9/big_55fb3262cb79524df41ee8ffa33b0cc9.png') no-repeat center center fixed; /* 背景图片URL */
            background-size: cover;
        }
        .login-box {
            width: 400px;
            padding: 40px;
            background-color: rgba(255, 255, 255, 0.9); /* 白色背景，透明度 */
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            text-align: center;
        }
        .login-title {
            font-size: 24px;
            margin-bottom: 10px;
            font-weight: bold;
            color: #343a40;
        }
        .login-description {
            font-size: 14px;
            margin-bottom: 30px;
            color: #6c757d;
        }
        .layui-form-item {
            margin-bottom: 20px;
        }
        .layui-input {
            height: 45px;
            border-radius: 5px;
        }
        .layui-btn {
            width: 100%;
            height: 45px;
            border-radius: 5px;
            background-color: #007bff;
            border-color: #007bff;
        }
        .layui-btn:hover {
            background-color: #0056b3;
        }
        .login-footer {
            margin-top: 20px;
            font-size: 14px;
            color: #6c757d;
        }
        .login-footer a {
            color: #007bff;
            text-decoration: none;
        }
        .login-footer a:hover {
            text-decoration: underline;
        }
        .error-message {
            color: red;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
<div class="login-box">
    <div class="login-title">用户登录</div>
    <div class="login-description">欢迎使用我们的点餐系统！！！</div>
    <form class="layui-form" action="/login" method="post">
        <div class="layui-form-item">
            <input type="text" name="email" lay-verify="required|email" placeholder="请输入邮箱" autocomplete="off" class="layui-input" value="">
        </div>
        <div class="layui-form-item">
            <input type="password" name="password" lay-verify="required|password" placeholder="请输入密码" autocomplete="off" class="layui-input" value="">
        </div>
        <div class="error-message">${message}</div>
        <div class="layui-form-item">
            <button class="layui-btn" lay-submit lay-filter="login">登录</button>
        </div>
    </form>


</div>
<script>
    layui.use('form', function(){
        var form = layui.form;
        // 自定义表单验证
        form.verify({
            email: function(value){
                if(value.length < 5){
                    return '邮箱至少需要5个字符';
                }
            },
            password: function(value){
                if(value.length < 6){
                    return '密码至少需要6个字符';
                }
            }
        });
    });
</script>
</body>
</html>
