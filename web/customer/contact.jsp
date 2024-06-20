<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>联系我们</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <style>
        .contact-container {
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body class="bg-gray-100">
<jsp:include page="/nav/customerNav.jsp"/>

<div class="contact-container bg-white p-8 rounded-lg shadow-lg">
    <div class="contact-header text-center mb-8">
        <h2 class="text-3xl font-bold">联系我们</h2>
    </div>
    <form class="space-y-6" action="#" method="post">
        <div>
            <label class="block text-sm font-medium text-gray-700">姓名</label>
            <input type="text" name="name" required placeholder="请输入姓名" value="${user.name}" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm">
        </div>

        <div>
            <label class="block text-sm font-medium text-gray-700">邮箱</label>
            <input type="email" name="email" required placeholder="请输入邮箱" value="${user.email}" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm">
        </div>

        <div>
            <label class="block text-sm font-medium text-gray-700">电话</label>
            <input type="tel" name="phone" required placeholder="请输入电话" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm">
        </div>

        <div>
            <label class="block text-sm font-medium text-gray-700">消息</label>
            <textarea name="message" placeholder="请输入您的消息" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"></textarea>
        </div>

        <div class="flex items-center justify-between">
            <button type="reset" class="bg-gray-200 hover:bg-gray-300 text-gray-700 font-bold py-2 px-4 rounded">重置</button>
            <button type="submit" class="bg-indigo-500 hover:bg-indigo-700 text-white font-bold py-2 px-4 rounded">提交</button>
        </div>
    </form>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        document.querySelector('form').addEventListener('submit', function (e) {
            e.preventDefault();
            const data = new FormData(e.target);
            const formData = Object.fromEntries(data.entries());
            console.log(formData);
            alert("感谢您的反馈，我们会在看见的第一时间联系您。")
            location.reload()
        });
    });
</script>
</body>
</html>
