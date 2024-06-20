<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <!-- 引入Tailwind CSS -->
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <style>
        .nav-bg {
            background-color: #1E40AF;
        }
    </style>
</head>
<body class="bg-gray-100">
<!-- 导航栏 -->
<nav class="nav-bg p-4 shadow-lg">
    <div class="container mx-auto flex justify-between items-center">
        <ul class="flex space-x-4">
            <li><a href="/cook/ordering" class="text-white hover:text-blue-300">订单</a></li>
            <li><a href="/cook/menu" class="text-white hover:text-blue-300">菜品</a></li>
            <li><a href="/cook/ingredient" class="text-white hover:text-blue-300">库存</a></li>
        </ul>
        <div class="flex space-x-4 items-center">
            <span class="text-white">${user.name}</span>
            <a href="/logout" class="text-white hover:text-blue-300">退出</a>
        </div>
    </div>
</nav>




<script src="https://cdn.jsdelivr.net/npm/@tailwindcss/typography@0.5.2/dist/typography.min.js"></script>
</body>
</html>
