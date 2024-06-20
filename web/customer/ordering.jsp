<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>订单详情</title>
    <!-- 引入Tailwind CSS -->
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <!-- 引入jQuery -->
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <style>
        .status-pending {
            color: orange;
        }
        .status-paid {
            color: green;
        }
        .status-cancelled {
            color: red;
        }
        .item-status-waiting {
            color: orange;
        }
        .item-status-cooking {
            color: blue;
        }
        .item-status-cancelled {
            color: red;
        }
        .item-status-served {
            color: green;
        }
        .retreat-button {
            color: white;
            background-color: red;
            border: none;
            padding: 5px 10px;
            cursor: pointer;
        }
    </style>
</head>
<body class="bg-gray-100">
<jsp:include page="/nav/customerNav.jsp"/>
<div class="container mx-auto my-10 p-8 bg-white rounded-lg shadow-lg">
    <div class="text-center mb-8">
        <h1 class="text-4xl font-bold">订单详情</h1>
    </div>
    <div class="bg-gray-100 p-6 rounded-lg mb-6">
        <div class="grid grid-cols-2 gap-4">
            <div><strong>订单ID:</strong> ${orderDetails.order.orderId}</div>
            <div><strong>用户:</strong> ${user.name}</div>
            <div><strong>用餐时间:</strong> <span id="orderCreatedAt"></span></div>
            <div class="order-status-${orderDetails.order.status}">
                <strong>订单状态:</strong>
                <c:choose>
                    <c:when test="${orderDetails.order.status == 0}"><span class="status-pending">待支付</span></c:when>
                    <c:when test="${orderDetails.order.status == 1}"><span class="status-paid">已支付</span></c:when>
                    <c:when test="${orderDetails.order.status == 2}"><span class="status-cancelled">已取消</span></c:when>
                </c:choose>
            </div>
        </div>
    </div>
    <div class="overflow-x-auto">
        <table class="min-w-full bg-white border border-gray-200">
            <thead class="bg-gray-200">
            <tr>
                <th class="py-2 px-4 border-b">菜单ID</th>
                <th class="py-2 px-4 border-b">菜单名称</th>
                <th class="py-2 px-4 border-b">数量</th>
                <th class="py-2 px-4 border-b">价格</th>
                <th class="py-2 px-4 border-b">状态</th>
                <th class="py-2 px-4 border-b">下单时间</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="totalAmount" value="0" />
            <c:forEach items="${orderDetails.orderItems}" var="item">
                <tr class="hover:bg-gray-50">
                    <td class="py-4 px-6 border-b text-center">${item.menu.menuId}</td>
                    <td class="py-4 px-6 border-b text-center">${item.menu.name}</td>
                    <td class="py-4 px-6 border-b text-center">${item.orderItem.quantity}</td>
                    <td class="py-4 px-6 border-b text-center">${item.menu.price}</td>
                    <td class="py-4 px-6 border-b text-center item-status-${item.orderItem.status}">
                        <c:choose>
                            <c:when test="${item.orderItem.status == 0}"><span class="item-status-waiting">等待</span></c:when>
                            <c:when test="${item.orderItem.status == 1}"><span class="item-status-cooking">制作中</span></c:when>
                            <c:when test="${item.orderItem.status == 2}"><span class="item-status-cancelled">退菜</span></c:when>
                            <c:when test="${item.orderItem.status == 3}"><span class="item-status-served">已上菜</span></c:when>
                        </c:choose>
                        <c:if test="${item.orderItem.status == 0}">
                            <button class="retreat-button ml-2"
                                    onclick="confirmRetreat(${item.orderItem.orderItemId})">退菜
                            </button>
                        </c:if>
                    </td>
                    <td class="py-4 px-6 border-b text-center"><span class="orderItemCreatedAt">${item.orderItem.createdAt}</span></td>
                    <c:if test="${item.orderItem.status != 2}">
                        <c:set var="totalAmount" value="${totalAmount + (item.menu.price * item.orderItem.quantity)}" />
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="text-right mt-6">
        <span class="text-2xl font-bold">订单总金额: ￥${totalAmount}</span>
    </div>
</div>

<script>
    // 初始化时间显示
    document.addEventListener('DOMContentLoaded', function () {
        updateOrderTime();

        // 设置菜品下单时间格式
        document.querySelectorAll('.orderItemCreatedAt').forEach(function (span) {
            span.textContent = formatDate(new Date(span.textContent));
        });

        // 定时刷新订单创建时间
        setInterval(updateOrderTime, 60000);
    });

    // 时间格式化函数
    function formatDate(date) {
        return date.toLocaleString('zh-CN', {timeZone: 'Asia/Shanghai'});
    }

    // 更新订单创建时间
    function updateOrderTime() {
        var createdAt = new Date("${orderDetails.order.createdAt}");
        var now = new Date();
        var diffMinutes = Math.floor((now - createdAt) / 60000);
        document.getElementById('orderCreatedAt').textContent = diffMinutes + " 分钟";
    }

    // 确认退菜函数
    function confirmRetreat(orderItemId) {
        if (confirm('确定要退菜吗？')) {
            // 确定退菜，向后端发送请求
            $.ajax({
                type: "POST",
                url: "/customer/ordering",
                data: {orderItemId: orderItemId},
                success: function (response) {
                    alert('退菜成功。');
                    // 重新加载页面
                    location.reload();
                },
                error: function (xhr, status, error) {
                    // 请求失败，显示错误信息
                    alert('退菜失败，请重试。');
                }
            });
        }
    }
</script>
</body>
</html>
