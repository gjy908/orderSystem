<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>餐馆列表</title>
    <!-- 引入Tailwind CSS -->
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-gray-100">
<jsp:include page="/nav/customerNav.jsp"/>
<div class="container mx-auto my-10 p-6 bg-white rounded-lg shadow-md">
    <h1 class="text-3xl font-bold mb-8">餐馆列表</h1>

    <!-- 餐馆列表 -->
    <div class="divide-y divide-gray-200" id="restaurant-list">
        <c:forEach var="restaurant" items="${restaurantList}">
            <div class="restaurant-item flex items-center p-6 hover:bg-gray-50 cursor-pointer" onclick="goToRestaurantDetail(${restaurant.restaurantId})">
                <img class="w-24 h-24 object-cover rounded-lg mr-6" src="${restaurant.imageUrl}" alt="餐馆图片">
                <div class="restaurant-item-info flex-1">
                    <div class="restaurant-item-title text-2xl font-bold mb-1">
                        <a href="javascript:void(0);">${restaurant.name}</a>
                    </div>
                    <div class="restaurant-item-location text-gray-600 mb-1">地址：${restaurant.location}</div>
                    <div class="restaurant-item-rating text-orange-500 text-lg mb-1">评分：${restaurant.rating}分</div>
                    <div class="restaurant-item-description text-gray-700 mb-1">${restaurant.description}</div>
                    <div class="restaurant-item-created-at text-gray-500 text-sm">成立时间：${restaurant.createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}</div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<!-- 引入Tailwind CSS的JS插件 -->
<script src="https://cdn.jsdelivr.net/npm/@tailwindcss/typography@0.5.2/dist/typography.min.js"></script>

<script>
    function goToRestaurantDetail(restaurantId) {
        window.location.href = "/customer/table?rId=" + restaurantId;
    }
</script>

</body>
</html>
