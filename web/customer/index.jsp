<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>点餐系统首页</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-gray-100">
<!-- 导航栏 -->
<jsp:include page="/nav/customerNav.jsp"/>
<%--<div class="header bg-gray-800 text-white text-center py-8">--%>
<%--    <h1 class="text-4xl font-bold">欢迎来到我们的点餐系统</h1>--%>
<%--</div>--%>

<!-- 轮播图 -->
<div class="carousel mx-auto mt-10 mb-20 w-4/5">
    <div class="relative overflow-hidden rounded-lg shadow-lg">
        <div class="carousel-inner flex">
            <div class="carousel-item w-full">
                <img src="https://img.zcool.cn/community/01fc295d6c6fe8a80120526da4918a.jpg@3000w_1l_0o_100sh.jpg" alt="特色菜1" class="w-full h-auto">
            </div>
            <div class="carousel-item w-full">
                <img src="https://img.zcool.cn/community/01fc295d6c6fe8a80120526da4918a.jpg@3000w_1l_0o_100sh.jpg" alt="特色菜2" class="w-full h-auto">
            </div>
            <div class="carousel-item w-full">
                <img src="https://img.zcool.cn/community/01fc295d6c6fe8a80120526da4918a.jpg@3000w_1l_0o_100sh.jpg" alt="特色菜3" class="w-full h-auto">
            </div>
        </div>
    </div>
</div>

<!-- 内容区 -->
<div class="container mx-auto px-4">

    <div class="content-section mb-12">
        <h2 class="text-3xl font-bold text-center mb-6">热门推荐</h2>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
            <div class="menu-item bg-white p-6 rounded-lg shadow-md text-center">
                <img src="https://img.zcool.cn/community/01bcfd5e60b789a80120a895f1daf3.jpg@1280w_1l_2o_100sh.jpg" alt="推荐菜品1" class="w-full h-auto rounded-lg">
                <h3 class="text-xl font-bold mt-4">推荐菜品1</h3>
                <p class="text-gray-600 mt-2">价格: ¥35</p>
             </div>
            <div class="menu-item bg-white p-6 rounded-lg shadow-md text-center">
                <img src="https://img.zcool.cn/community/01bcfd5e60b789a80120a895f1daf3.jpg@1280w_1l_2o_100sh.jpg" alt="推荐菜品2" class="w-full h-auto rounded-lg">
                <h3 class="text-xl font-bold mt-4">推荐菜品2</h3>
                <p class="text-gray-600 mt-2">价格: ¥45</p>
             </div>
            <div class="menu-item bg-white p-6 rounded-lg shadow-md text-center">
                <img src="https://img.zcool.cn/community/01bcfd5e60b789a80120a895f1daf3.jpg@1280w_1l_2o_100sh.jpg" alt="推荐菜品3" class="w-full h-auto rounded-lg">
                <h3 class="text-xl font-bold mt-4">推荐菜品3</h3>
                <p class="text-gray-600 mt-2">价格: ¥55</p>
             </div>
        </div>
    </div>
</div>



<!-- 轮播图JS -->
<script>
    document.addEventListener('DOMContentLoaded', function () {
        const carouselItems = document.querySelectorAll('.carousel-item');
        let currentIndex = 0;

        function showCarouselItem(index) {
            carouselItems.forEach((item, i) => {
                item.classList.toggle('hidden', i !== index);
            });
        }

        function nextCarouselItem() {
            currentIndex = (currentIndex + 1) % carouselItems.length;
            showCarouselItem(currentIndex);
        }

        setInterval(nextCarouselItem, 3000); // 每3秒切换一次
        showCarouselItem(currentIndex);
    });
</script>
</body>
</html>
