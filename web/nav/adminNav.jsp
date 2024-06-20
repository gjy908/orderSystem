<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <!-- 引入Tailwind CSS -->
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <style>
        .navbar-container {
            background-color: #1E40AF; /* 深蓝色背景 */
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 阴影效果 */
        }

        .navbar-container ul {
            display: flex;
            justify-content: flex-end; /* 右侧对齐 */
        }

        .navbar-container li {
            list-style: none;
        }

        .navbar-container a {
            color: #f8f9fa; /* 文字颜色 */
            font-weight: bold;
            padding: 15px 20px;
            display: block;
            text-decoration: none;
            transition: background-color 0.3s ease; /* 过渡效果 */
        }

        .navbar-container a:hover {
            color: #1E40AF; /* 悬停时文字颜色 */
            background-color: #FFC107; /* 悬停时背景色 */
        }

        .navbar-container .active a {
            color: #fff;
            background-color: #007bff; /* 当前活动项背景色 */
        }

        .dropdown:hover .dropdown-content {
            display: block;
        }

        .dropdown-content {
            display: none;
            position: absolute;
            background-color: #1E40AF; /* 下拉菜单背景色 */
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1); /* 阴影效果 */
            z-index: 1;
            right: 0;
            min-width: 160px;
            border-radius: 0.25rem;
        }

        .dropdown-content a {
            padding: 10px 20px;
            color: #f8f9fa;
            display: block;
            text-align: left;
        }

        .dropdown-content a:hover {
            color: #1E40AF;
            background-color: #FFC107;
        }

        .content {
            flex: 1;
            padding: 20px;
            overflow: auto;
        }
    </style>
</head>
<body class="bg-gray-100 flex flex-col min-h-screen">
<div class="navbar-container p-4 shadow-lg">
    <div class="container mx-auto flex justify-between items-center relative">
        <ul class="flex space-x-4">
            <li><a href="/admin/ordering">结算</a></li>
            <li><a href="/admin/tongji">营业统计</a></li>
        </ul>
        <div class="relative">
            <div class="dropdown inline-block relative">
                <a href="javascript:;" class="hover:bg-blue-700 px-4 py-2 rounded">${user.name}</a>
                <div class="dropdown-content absolute hidden">
                    <a href="/logout" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">退出</a>
                </div>
            </div>
        </div>
    </div>
</div>


</body>
</html>
